#include <iostream>
#include <algorithm>
#include <math.h>
#include <vector>
#include <unordered_set>
#include <queue>
#include <list>

#define MAX_WATER_LEVEL 10000
#define MIN(a, b) ((a < (b)) ? (a) : (b)


struct chamber {
	int chamber_num;
	bool visited;
	std::vector<std::pair<int, int>> tunnels;
};
	

// Union find structure
template <class T> struct UnionFindNode {
	T val;
	UnionFindNode<T> *parent;
	int size;
	bool is_control_node;
	int min_level;
};

template <class T> class UnionFind {
public:
	static UnionFindNode<T>* MakeSet(T val, int water_level) {
		UnionFindNode<T> *node = new UnionFindNode<T>();
		node->val = val;
		node->parent = node;
		node->size = 1;

		node->is_control_node = false;
		node->min_level = water_level;

		return node;
	}

	static UnionFindNode<T>* Find(UnionFindNode<T> *node) {
		if (node->parent == node)
			return node;

		return Find(node->parent);
	}

	static void Union(UnionFindNode<T> *a, UnionFindNode<T> *b) {
		UnionFindNode<T> *root_a = UnionFind::Find(a);
		UnionFindNode<T> *root_b = UnionFind::Find(b);

		int min = MIN(root_a->min_level, root_b->min_level); 

		if (root_a->size <= root_b->size) {
			root_a->parent = root_b;
			root_b->min_level = min;
			root_b->size += root_a->size;
		
		} else {
			root_b->parent = root_a;
			root_a->min_level = min;
			root_a->size += root_b->size;
		}
	}
};


#define MAX_CHAMBERS 10000

int num_chambers;
int num_hallways;
int num_control_chambers;
int water_level;

std::vector<std::pair<int, std::pair<int, int>>> hallways;
UnionFindNode<struct chamber *> *chambers[MAX_CHAMBERS];

void solve(int iteration_num)
{
	// Main logic
	// Clear previous
	hallways.clear();

	std::cin >> num_chambers >> num_hallways >> num_control_chambers >> water_level;

	for (int i = 0; i < num_chambers; ++i) {
		struct chamber *ch = new struct chamber();
		ch->chamber_num = i;
		ch->visited = false;
		chambers[i] = UnionFind<struct chamber *>::MakeSet(ch, water_level);
	}

	int source, dest, min_lvl;
	for (int i = 0; i < num_hallways; ++i) {
		std::cin >> source >> dest >> min_lvl;
		source -= 1;
		dest -= 1;

		hallways.push_back(std::make_pair(-1 * min_lvl, std::make_pair(source, dest)));
	}

	for (int i = 0; i < num_control_chambers; ++i) {
		std::cin >> source >> min_lvl;
		source -= 1;

		chambers[source]->is_control_node = true;
		chambers[source]->min_level = min_lvl;
	}

	// we have the sorted vertices
	std::sort(hallways.begin(), hallways.end());

	int minimum_water_level = 99999;
	for (auto dist_it = hallways.begin(); dist_it != hallways.end(); ++dist_it) {
		UnionFindNode<struct chamber *> *a = chambers[dist_it->second.first];
		UnionFindNode<struct chamber *> *b = chambers[dist_it->second.second];

		if (UnionFind<struct chamber *>::Find(a) == UnionFind<struct chamber *>::Find(b))
			continue;

		UnionFind<struct chamber *>::Union(a, b);
		a->val->tunnels.push_back(std::make_pair(-1 * dist_it->first, b->val->chamber_num));
		b->val->tunnels.push_back(std::make_pair(-1 * dist_it->first, a->val->chamber_num));
//		std::cout << "Tree contains edge " << a->val->chamber_num << " <-> " << b->val->chamber_num << " with level " << -1 * dist_it->first << "\n";

		minimum_water_level = MIN(minimum_water_level, (-1 * dist_it->first));

		UnionFindNode<struct chamber *> *root = UnionFind<struct chamber *>::Find(a);

/*
		std::cout << "Merged " << a->val + 1 << "-" << b->val + 1 << " Size: ";
		std::cout << root->size << " Cost: " << root->cost << "\n";
*/		
		if (root->size == num_chambers) {
			break;
		}
	}

	minimum_water_level = MIN(minimum_water_level, water_level);

	std::priority_queue<std::pair<int, int>> mst;
	int total_water_level = water_level;
	bool possible = true;

	for (auto it = chambers[0]->val->tunnels.begin(); it != chambers[0]->val->tunnels.end(); ++it)
		mst.push(*it);

	chambers[0]->val->visited = true;
	
	if (chambers[0]->is_control_node)
		total_water_level = MIN(total_water_level, chambers[0]->min_level);

	while (!mst.empty() && total_water_level > minimum_water_level && minimum_water_level >= 0) {
		auto edge = mst.top();
		mst.pop();

//		std::cout << "trying edge to " << edge.second << " with level " << edge.first << " for water level " << total_water_level << "\n";

		if (chambers[edge.second]->val->visited)
			continue;

		if (edge.first < total_water_level) {
			possible = false;
			break;
		}
		
		chambers[edge.second]->val->visited = true;

		for (auto it = chambers[edge.second]->val->tunnels.begin(); it != chambers[edge.second]->val->tunnels.end(); ++it) {
			if (chambers[it->second]->val->visited)
				continue;

			mst.push(*it);
		}
		
		if (chambers[edge.second]->is_control_node) {
			int node_water_level = chambers[edge.second]->min_level;
			total_water_level = MIN(total_water_level, node_water_level);
//			std::cout << "New water level " << total_water_level << "\n";
		}
	}
	

	if (minimum_water_level < 0)
		possible = false;

	std::cout << "Case #" << iteration_num << ": ";
	if (!possible)
		std::cout << "impossible\n";
	else
		std::cout << minimum_water_level << "\n";
}

int main()
{
	std::ios_base::sync_with_stdio(false);

	int t;
	std::cin >> t;

	for (int i = 0; i < t; ++i)
		solve(i + 1);

	return 0;
}


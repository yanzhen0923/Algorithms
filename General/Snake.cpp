#include <iostream>
#include <algorithm>
#include <deque>

#define MAX_SIZE 10000

#define EMPTY 0
#define SNAKE_PART 1
#define FOOD 2

#define RIGHT 0
#define UP 1
#define LEFT 2
#define DOWN 3

#define COMMAND_FRONT 'F'
#define COMMAND_RIGHT 'R'
#define COMMAND_LEFT 'L'

int field[MAX_SIZE][MAX_SIZE];
std::deque<std::pair<int, int>> snake;

int dir_vect[] = {RIGHT, DOWN, LEFT, UP};

int current_dir;
int current_dir_index;

int food;
int turns;

int field_size;
bool alive;

void print_dir(int dir)
{
	switch (dir) {
	case RIGHT:
		std::cout << "RIGHT\n";
		break;

	case LEFT:
		std::cout << "LEFT\n";
		break;

	case UP:
		std::cout << "UP\n";
		break;

	case DOWN:
		std::cout << "DOWN\n";
		break;
	}
}

void switch_next_dir(char command)
{
	switch (command) {
	case COMMAND_RIGHT:
		current_dir_index = (current_dir_index + 1) % 4;
		break;

	case COMMAND_LEFT:
		current_dir_index = (current_dir_index - 1 + 4) % 4;

	default:
		break;
	}

	current_dir = dir_vect[current_dir_index];
}

void get_next_pos(std::pair<int, int> &current_pos)
{
	switch (current_dir) {
	case UP:
		//std::cout << "UP\n";
		current_pos.first = (current_pos.first - 1 + field_size) % field_size;
		//std::cout << "FIRST: " << current_pos.first << "\n";
		break;

	case DOWN:
		//std::cout << "DOWN\n";
		current_pos.first = (current_pos.first + 1) % field_size;
		break;

	case RIGHT:
		//std::cout << "RIGHT\n";
		current_pos.second = (current_pos.second + 1) % field_size;
		break;

	case LEFT:
		//std::cout << "LEFT\n";
		current_pos.second = (current_pos.second - 1 + field_size) % field_size;
		break;
	}
}

void print_field()
{
	for (int i = 0; i < field_size; ++i) {
		for (int j = 0; j < field_size; ++j) {
			switch (field[i][j]) {
			case EMPTY:
				std::cout << "0 ";
				break;
			
			case SNAKE_PART:
				std::cout << "X ";
				break;

			case FOOD:
				std::cout << "F ";
				break;
			}

		}

		std::cout << "\n";
	}

}

void do_move(char command)
{
	//print_field();
	//std::cout << "Current dir: ";
	//print_dir(current_dir);

	//std::cout << "Command: " << command << "\n";

	switch_next_dir(command);

	//std::cout << "New dir: ";
	//print_dir(current_dir);
	
	std::pair<int, int> head_pos = snake.front();
	//std::cout << "Snake head: " << head_pos.first << " " << head_pos.second << "\n";
	get_next_pos(head_pos);
	//std::cout << "New pos: " << head_pos.first << " " << head_pos.second << "\n";

	if (field[head_pos.first][head_pos.second] != FOOD) {
		// Snake does not grow
		// Cut the tail
		std::pair<int, int> tail = snake.back();
		snake.pop_back();
		field[tail.first][tail.second] = EMPTY;
	
	} else {
		// Increase score
		++food;
	} 

	if (field[head_pos.first][head_pos.second] == SNAKE_PART) {
		// He dead.
		alive = false;
		return;
	}

	// Move the head
	snake.push_front(head_pos);
	field[head_pos.first][head_pos.second] = SNAKE_PART;

	++turns;
}


void solve(int iteration_num)
{
	// Main logic
	// cleanup
	for (int i = 0; i < MAX_SIZE; ++i)
		for (int j = 0; j < MAX_SIZE; ++j)
			field[i][j] = EMPTY;

	food = 0;
	turns = 0;
	current_dir_index = RIGHT;
	current_dir = dir_vect[current_dir_index];
	field_size = 0;
	alive = true;

	snake.clear();

	// inputs
	int food_blocks, sx, sy, x, y, w, h;
	std::cin >> field_size >> food_blocks;

	// snake's starting coords
	std::cin >> sx >> sy;
	sx -= 1;
	sy -= 1;
	//sy = field_size - sy - 1;
	//std::cout << "READ_HEAD: " << sx <<  " " << sy << "\n";
	//std::cout << "Actual head: " << sx << " " << sy << "\n";
	snake.push_front(std::make_pair(sy, sx));

	for (int i = 0; i < food_blocks; ++i) {
		std::cin >> x >> y >> w >> h;
		x -= 1;
		y -= 1;
		//y = field_size - 1 - y;

		for (int j = 0; j < w; ++j)
			for (int k = 0; k < h; ++k) {
				field[(y + k) % field_size][(x + j) % field_size] = FOOD;
	//			std::cout << "FOOD at " << (x + j) % field_size << " " << (y + k) % field_size << "\n";
			}
	}

	field[sy][sx] = SNAKE_PART;

	char command;
	int input_len;
	std::cin >> input_len;

	for (int i = 0; i < input_len; ++i) {
		std::cin >> command;
	
		if (alive)
			do_move(command);
	}

	//std::cout << "\n";

	std::cout << "Case #" << iteration_num << ": " << turns << " " << food << "\n";
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


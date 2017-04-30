#include <iostream>
#include <algorithm>
#include <stdio.h>

void solve(int iteration_num)
{
	// Main logic
	std::string num;
	int var = 0;;

	std::cin >> num;

	for (int i = 0; i < num.length(); ++i)
		var = ((var * 8) + (num[i] - '0'))  % 13;

	var = (var + 3) % 13;

	if (var == 0)
		var = 13;

	std::cout << "Case #" << iteration_num << ": " << var << "\n";
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


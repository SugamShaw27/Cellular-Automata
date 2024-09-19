import numpy as np
import matplotlib.pyplot as plt
import matplotlib.colors as mcolors

def initialize_grid(height, width, prob_alive,choice):
    print(choice)
    if(choice=="glider"):
        print("height: ",height)
        grid = np.zeros((height, height), dtype=int)
        glider = np.array([
        [0, 1, 0],
        [0, 0, 1],
        [1, 1, 1]
        ])
        glider_positions = [(10, 10), (20, 20),(30,30),(10,20),(10,30),(20,10),(20,30),(30,10),(30,20)]
        for start_x, start_y in glider_positions:
            if (start_x + 3 <= height) and (start_y + 3 <= height):
                grid[start_x:start_x+3, start_y:start_y+3] = glider
        print(grid)
        return grid
    elif(choice=="pulsar"):
        grid = np.zeros((height, height), dtype=int)
        pulsar_pattern = [
        (2, 4), (2, 5), (2, 6), (2, 10), (2, 11), (2, 12),
        (7, 4), (7, 5), (7, 6), (7, 10), (7, 11), (7, 12),
        (9, 4), (9, 5), (9, 6), (9, 10), (9, 11), (9, 12),
        (14, 4), (14, 5), (14, 6), (14, 10), (14, 11), (14, 12),
        (4, 2), (5, 2), (6, 2), (10, 2), (11, 2), (12, 2),
        (4, 7), (5, 7), (6, 7), (10, 7), (11, 7), (12, 7),
        (4, 9), (5, 9), (6, 9), (10, 9), (11, 9), (12, 9),
        (4, 14), (5, 14), (6, 14), (10, 14), (11, 14), (12, 14)
        ]
        offset_x = (height - 17) // 2
        offset_y = (height - 17) // 2

        for (x, y) in pulsar_pattern:
            grid[x + offset_x, y + offset_y] = 1
        return grid
    elif(choice=="glidergun"):
        grid = np.zeros((height, width), dtype=int)
        gun_pattern = [
        (5, 1), (5, 2), (6, 1), (6, 2),
        (3, 13), (3, 14), (4, 12), (4, 16), (5, 11), (5, 17), (6, 11), (6, 15), (6, 17), (6, 18), (7, 11), (7, 17),
        (8, 12), (8, 16), (9, 13), (9, 14),
        (1, 25), (2, 23), (2, 25), (3, 21), (3, 22), (4, 21), (4, 22), (5, 21), (5, 22), (6, 23), (6, 25), (7, 25),
        (3, 35), (3, 36), (4, 35), (4, 36)
        ]
        # pattern_width = 37  # Max y coordinate + 1
        # pattern_height = 10  # Max x coordinate + 1
        # offset_x = (height - pattern_height) // 2
        # offset_y = (width - pattern_width) // 2
        # for (x, y) in gun_pattern:
        #     grid[x + offset_x, y + offset_y] = 1
        for (x, y) in gun_pattern:
            grid[x,y] = 1
        return grid
    return np.random.choice([0, 1], size=(height, width), p=[1 - prob_alive, prob_alive])


def apply_game_of_life_rule_glider(grid):
    return apply_game_of_life_rule(grid)
def apply_game_of_life_rule_pulsar(grid):
    return apply_game_of_life_rule(grid)
def apply_game_of_life_rule_glider_gun(grid):
    try:
        grid = np.array(grid)
        if grid.ndim != 2:
            raise ValueError("Grid must be a 2D array")
        height, width = grid.shape
        # Create a padded grid with zeros on the boundary
        padded_grid = np.pad(grid, pad_width=1, mode='constant', constant_values=0)
        new_grid = np.zeros_like(grid)
        for i in range(height):
            for j in range(width):
                # Calculate the sum of the 3x3 neighborhood
                neighbors = padded_grid[i:i + 3, j:j + 3]
                num_alive_neighbors = np.sum(neighbors) - grid[i, j]
                if grid[i, j] == 1:
                    if num_alive_neighbors < 2 or num_alive_neighbors > 3:
                        new_grid[i, j] = 0  # Cell dies
                    else:
                        new_grid[i, j] = 1  # Cell lives
                else:
                    if num_alive_neighbors == 3:
                        new_grid[i, j] = 1  # Cell becomes alive
        return new_grid.tolist()
    except Exception as e:
        raise ValueError(f"Error in apply_game_of_life_rule: {str(e)}")
def apply_game_of_life_rule(grid):
    try:
        grid = np.array(grid)
        if grid.ndim != 2:
            raise ValueError("Grid must be a 2D array")
        height, width = grid.shape
        new_grid = np.zeros_like(grid)
        for i in range(height):
            for j in range(width):
                neighbors = grid.take(range(i - 1, i + 2), mode="wrap", axis=0).take(
                    range(j - 1, j + 2), mode="wrap", axis=1
                )
                num_alive_neighbors = np.sum(neighbors) - grid[i, j]
                if grid[i, j] == 1:
                    if num_alive_neighbors < 2 or num_alive_neighbors > 3:
                        new_grid[i, j] = 0  # Cell dies
                    else:
                        new_grid[i, j] = 1  # Cell lives
                else:
                    if num_alive_neighbors == 3:
                        new_grid[i, j] = 1  # Cell becomes alive
        return new_grid.tolist()
    except Exception as e:
        raise ValueError(f"Error in apply_game_of_life_rule: {str(e)}")

def apply_majority_rule_xor_von_neumann(grid):
    try:
        # Convert the grid to a numpy array and check its shape
        grid = np.array(grid)
        print("Grid shape:", grid.shape)
        # print("Grid data:", grid)

        if grid.ndim != 2:
            raise ValueError("Grid must be a 2D array")

        height, width = grid.shape
        new_grid = np.zeros_like(grid)
        for i in range(height):
            for j in range(width):
                num_alive_neighbors = grid[i,j]^grid[(i - 1) % height, j]^grid[i, (j - 1) % width]^grid[i, (j + 1) % width]^grid[(i + 1) % height, j]
                new_grid[i, j] = num_alive_neighbors
        return new_grid.tolist()
    except Exception as e:
        raise ValueError(f"Error in apply_majority_rule_xor_von_neumann: {str(e)}")
def apply_majority_rule_xor_moore_rule(grid):
    try:
        # Convert the grid to a numpy array and check its shape
        grid = np.array(grid)
        print("Grid shape:", grid.shape)
        # print("Grid data:", grid)

        if grid.ndim != 2:
            raise ValueError("Grid must be a 2D array")

        height, width = grid.shape
        new_grid = np.zeros_like(grid)
        for i in range(height):
            for j in range(width):
                neighbors = grid.take(range(i - 1, i + 2), mode="wrap", axis=0).take(
                    range(j - 1, j + 2), mode="wrap", axis=1
                )
                xor_result=0
                for a in range(3):
                    for b in range(3):
                        xor_result^=neighbors[a,b]
                        # if a!=1 and b!=1:
                        #     xor_result^=neighbors[a,b]
                new_grid[i, j] = xor_result
        return new_grid.tolist()
    except Exception as e:
        raise ValueError(f"Error in apply_majority_rule_xor_moore_rule: {str(e)}")

def apply_sum_mod_2_xor_moore_rule(grid):
    try:
        # Convert the grid to a numpy array and check its shape
        grid = np.array(grid)
        print("Grid shape:", grid.shape)
        # print("Grid data:", grid)

        if grid.ndim != 2:
            raise ValueError("Grid must be a 2D array")

        height, width = grid.shape
        new_grid = np.zeros_like(grid)
        for i in range(height):
            for j in range(width):
                neighbors = grid.take(range(i - 1, i + 2), mode="wrap", axis=0).take(
                    range(j - 1, j + 2), mode="wrap", axis=1
                )
                # num_alive_neighbors = (
                #     np.sum(neighbors) - grid[i, j]
                # )
                num_alive_neighbors = np.sum(neighbors)
                new_grid[i, j] = num_alive_neighbors % 2
        return new_grid.tolist()
    except Exception as e:
        raise ValueError(f"Error in apply_sum_mod_2_xor_moore_rule: {str(e)}")

def apply_sum_mod_2_xor_von_neumann(grid):
    try:
        # Convert the grid to a numpy array and check its shape
        grid = np.array(grid)
        print("Grid shape:", grid.shape)
        # print("Grid data:", grid)

        if grid.ndim != 2:
            raise ValueError("Grid must be a 2D array")

        height, width = grid.shape
        new_grid = np.zeros_like(grid)
        for i in range(height):
            for j in range(width):
                neighbors = [
                    grid[(i - 1) % height, j],
                    grid[i, (j - 1) % width],
                    grid[i, (j + 1) % width],
                    grid[(i + 1) % height, j],
                ]
                num_alive_neighbors = sum(neighbors)+grid[i, j]
                new_grid[i, j] = num_alive_neighbors % 2
        return new_grid.tolist()
    except Exception as e:
        raise ValueError(f"Error in apply_sum_mod_2_xor_von_neumann: {str(e)}")


def get_schema_color_code(schema_name):
    try:
        cmap = plt.get_cmap(schema_name)
        # color_codes = [mcolors.rgb2hex(cmap(i)[:3]) for i in range(num_colors)] --> num_colors(0-256)
        color_codes = [mcolors.rgb2hex(cmap(0)[:3]),mcolors.rgb2hex(cmap(256)[:3])]
        return color_codes
    except ValueError:
        print(f"Error: '{schema_name}' is not a valid colormap name.")
        return None


# experiment no. 3 and 4
# uniform ca
# 1. No random initial configuration and random position ruled applied
# 2. Random initial configuration and random position ruled applied


import numpy as np
import matplotlib.pyplot as plt
from PIL import Image
import base64
from io import BytesIO
import seaborn as sns
import matplotlib.colors as mcolors



def rule_to_binary(rule_number):
    return np.array([int(x) for x in np.binary_repr(rule_number, width=8)])


def get_new_state(left, center, right, rule_binary):
    index = 7 - (left * 4 + center * 2 + right)
    return rule_binary[index]


def generate_initial_state(size, random):
    if random:
        state = np.random.randint(2, size=size, dtype=int)
        return state
    else:
        state = np.zeros(size, dtype=int)
        state[size // 2] = 1
        return state



def generate_next_state(current_state, rule_binary):
    size = len(current_state)
    new_state = np.zeros(size, dtype=int)
    n_matrix = []
    for i in range(size):
        indices = np.random.choice(size, 3, replace=False)
        left = current_state[indices[0]]
        center = current_state[indices[1]]
        right = current_state[indices[2]]
        n_matrix.append([indices[0], indices[1], indices[2]])
        new_state[i] = get_new_state(left, center, right, rule_binary)
    return new_state, n_matrix

def generate_cellular_automaton(current_state,
                                rule_binary,
                                size=40,
                                generations=20,
                                ):

    automaton = np.zeros((generations, size), dtype=int)
    automaton[0] = current_state
    number_matrix = []

    for i in range(1, generations):
        new_state, matrix = generate_next_state(current_state, rule_binary)
        automaton[i] = new_state
        current_state = new_state
        number_matrix.append(matrix)
    # print(number_matrix)
    return automaton, number_matrix

def convert_to_image(automaton,title,X_axis,Y_axiz, colormap='binary', cell_size=10):
    generations, size = automaton.shape

    # Calculate the figure size to simulate cell size
    fig_width = size * cell_size  # Convert to inches (DPI is 100)
    fig_height = generations * cell_size

    fig, ax = plt.subplots(figsize=(fig_width / 100, fig_height / 100), dpi=100)
    ax.imshow(automaton, cmap=colormap)

    ax.set_title(title, fontsize=12, pad=10)
    # Set x-axis and y-axis labels
    ax.set_xlabel(X_axis, fontsize=10)
    ax.set_ylabel(Y_axiz, fontsize=10)

    # Hide the ticks and frame but keep the labels
    ax.tick_params(axis='both', which='both', bottom=False, top=False, left=False, right=False)
    ax.spines['top'].set_visible(False)
    ax.spines['right'].set_visible(False)
    ax.spines['bottom'].set_visible(True)
    ax.spines['left'].set_visible(True)

    # Save the plot to a BytesIO object
    buf = BytesIO()
    plt.savefig(buf, format='png', bbox_inches='tight', pad_inches=0.1)
    plt.close(fig)

    buf.seek(0)
    img = Image.open(buf)
    return img


def number_matrix_save(matrix, rule_number):
    matrix_flat = [[" ".join(map(str, val)) for val in row] for row in matrix]
    # Create a grid to visualize the data
    fig, ax = plt.subplots(figsize=(15, 15))  # Increase the figure size

    # Display a blank heatmap
    sns.heatmap(
        np.zeros_like(matrix_flat, dtype=int),
        annot=matrix_flat,
        fmt="",
        cbar=False,
        ax=ax,
        linewidths=0.5,
        linecolor="white",
        annot_kws={"size": 10},
    )
    plt.title(
        f"Matrix with Integer Values rule {rule_number}",
        pad=10,
        fontsize=12,
    )
    plt.xlabel("Size", fontsize=12, labelpad=0)
    plt.ylabel(
        "Generation", fontsize=12, labelpad=0
    )
    plt.xticks(
        np.arange(len(matrix[0])) + 0.5,
        labels=range(len(matrix[0])),
        rotation=90,
        fontsize=8,
        )
    plt.yticks(
        np.arange(len(matrix)) + 0.5, labels=range(len(matrix)), rotation=0, fontsize=8
    )

    plt.tight_layout()

    # Save the plot to a BytesIO object
    buf = BytesIO()
    plt.savefig(buf, format='png', bbox_inches='tight', pad_inches=0.1)
    plt.close(fig)

    buf.seek(0)
    img = Image.open(buf)
    return img

def combine_images(automaton_img, matrix_img):
    # Combine two images side by side
    total_width = automaton_img.width + matrix_img.width
    max_height = max(automaton_img.height, matrix_img.height)

    combined_img = Image.new('RGB', (total_width, max_height))
    combined_img.paste(automaton_img, (0, 0))
    combined_img.paste(matrix_img, (automaton_img.width, 0))

    return combined_img

def generate_and_return_image(
        rule_number,
        size,
        generations,
        random_initial_state,
        title="",
        X_axis="",
        Y_axis="",
        colormap="binary",
        cell_size=10,
):
    rule_binary = rule_to_binary(rule_number)
    current_state = generate_initial_state(size,random=random_initial_state)
    automaton, number_matrix = generate_cellular_automaton(
        current_state,
        rule_binary,
        size=size,
        generations=generations,
    )

    automaton_img = convert_to_image(automaton, title, X_axis, Y_axis, colormap, cell_size)
    matrix_img = number_matrix_save(number_matrix, rule_number)

    combined_img = combine_images(automaton_img, matrix_img)

    buffered_automaton = BytesIO()
    automaton_img.save(buffered_automaton, format="PNG")
    img_str_automaton = base64.b64encode(buffered_automaton.getvalue()).decode("utf-8")

    buffered_combined = BytesIO()
    combined_img.save(buffered_combined, format="PNG")
    img_str_combined = base64.b64encode(buffered_combined.getvalue()).decode("utf-8")

    return img_str_automaton, img_str_combined

# Example usage:
# img_str_automaton, img_str_combined = generate_and_return_image(30, 40, 20, False, "Title", "X Axis", "Y Axis", colormap='viridis')


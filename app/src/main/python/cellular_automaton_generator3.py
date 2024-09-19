# experiment no. 5 and 6
# non uniform ca
# random initial state configuration of 0 and 1
# 1. No random position
# 2. Random position
import numpy as np
import matplotlib.pyplot as plt
import seaborn as sns
import matplotlib.colors as mcolors
import base64
from io import BytesIO
from PIL import Image


def rule_to_binary(rule_number):
    return np.array([int(x) for x in np.binary_repr(rule_number, width=8)])


def get_new_state(left, center, right, rule_number):
    rule_binary = rule_to_binary(rule_number)
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



def generate_random_initial_state(size):
    state = np.random.randint(2, size=size, dtype=int)
    return state


def generate_next_state(current_state, non_uniform_rule_binary_list, random=False):
    size = len(current_state)
    new_state = np.zeros(size, dtype=int)
    n_matrix = []
    indices = np.zeros(3, dtype=int)
    for i in range(size):
        if random == True:
            indices = np.random.choice(size, 3, replace=False)
        else:
            indices[0] = (i - 1) % size
            indices[1] = i
            indices[2] = (i + 1) % size
        left = current_state[indices[0]]
        center = current_state[indices[1]]
        right = current_state[indices[2]]
        n_matrix.append([indices[0], indices[1], indices[2]])
        new_state[i] = get_new_state(
            left, center, right, rule_number=non_uniform_rule_binary_list[i]
        )
    return new_state, n_matrix


def generate_cellular_automaton(
        non_uniform_rule_binary_list,
        automatoninitial,
        size=15,
        generations=20,
        random=False,
):

    automaton = np.copy(automatoninitial)

    number_matrix = []

    for i in range(1, generations):
        automaton[i], matrix = generate_next_state(
            automaton[i - 1], non_uniform_rule_binary_list, random
        )
        number_matrix.append(matrix)

    return automaton, number_matrix


def convert_to_image(automaton, title, X_axis, Y_axiz, colormap="binary", cell_size=10):
    generations, size = automaton.shape

    # Calculate the figure size to simulate cell size
    fig_width = size * cell_size  # Convert to inches (DPI is 100)
    fig_height = generations * cell_size

    fig, ax = plt.subplots(figsize=(fig_width / 100, fig_height / 100), dpi=100)
    ax.imshow(automaton, cmap=colormap)

    ax.set_title(title, fontsize=12, pad=10)
    # Set x-axis and y-axis labels
    ax.xaxis.set_label_position('top')
    ax.xaxis.tick_top()
    # ax.set_xticks(np.arange(size))
    # ax.set_yticks(np.arange(generations))
    # ax.set_xticklabels(np.arange(size))

    ax.set_xlabel(X_axis, fontsize=10)
    ax.set_ylabel(Y_axiz, fontsize=10)

    # Hide the ticks and frame but keep the labels
    ax.tick_params(axis='both', which='both', bottom=False, top=True, left=True, right=False)
    ax.spines['top'].set_visible(True)
    ax.spines['right'].set_visible(True)
    ax.spines['bottom'].set_visible(True)
    ax.spines['left'].set_visible(True)

    # Save the plot to a BytesIO object
    buf = BytesIO()
    plt.savefig(buf, format="png", bbox_inches="tight", pad_inches=0.1)
    plt.close(fig)

    buf.seek(0)
    img = Image.open(buf)
    return img

def number_matrix_save(matrix):
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
        f"Matrix with Integer Values of different rules",
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

def rule_vector_matrix_save(vector):
    # Convert the 1D vector to a 2D array with one row
    vector_2d = np.array([vector])

    # Create a grid to visualize the data
    fig, ax = plt.subplots(figsize=(10,4))  # Increase the figure size

    # Display a blank heatmap
    sns.heatmap(
        np.zeros_like(vector_2d, dtype=int),
        annot=vector_2d,
        fmt="",
        cbar=False,
        ax=ax,
        linewidths=0.5,
        linecolor="white",
        annot_kws={"size": 10},
    )
    plt.title(f"Rule Vector", pad=10, fontsize=12)
    plt.xlabel("Index", fontsize=12, labelpad=0)
    plt.ylabel("Rule", fontsize=12, labelpad=0)
    plt.xticks(
        np.arange(len(vector)) + 0.5, labels=range(len(vector)), rotation=90, fontsize=8,
        )
    plt.yticks([0.5], ["Rule"], rotation=0, fontsize=8)

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
        size,
        generations,
        random_position,
        title="",
        X_axis="",
        Y_axis="",
        colormap="binary",
        cell_size=10,
):
    non_uniform_rule_binary_list = np.random.randint(0, 255, size=size, dtype=int)

    automatoninitial = np.zeros((generations, size), dtype=int)
    automatoninitial[0] = generate_initial_state(size,"true")

    automaton, number_matrix = generate_cellular_automaton(
        non_uniform_rule_binary_list,
        automatoninitial,
        size=size,
        generations=generations,
        random=random_position,
    )

    buffered_automaton_initial = BytesIO()
    automaton_img_initial = convert_to_image(automatoninitial, title, X_axis, Y_axis, colormap, cell_size)
    automaton_img_initial.save(buffered_automaton_initial, format="PNG")
    img_str_automaton_inital = base64.b64encode(buffered_automaton_initial.getvalue()).decode("utf-8")


    buffered_automaton = BytesIO()
    automaton_img = convert_to_image(automaton, title, X_axis, Y_axis, colormap, cell_size)
    automaton_img.save(buffered_automaton, format="PNG")
    img_str_automaton = base64.b64encode(buffered_automaton.getvalue()).decode("utf-8")


    buffered_combined = BytesIO()
    matrix_img = number_matrix_save(number_matrix)
    combined_img = combine_images(automaton_img, matrix_img)
    combined_img.save(buffered_combined, format="PNG")
    img_str_combined = base64.b64encode(buffered_combined.getvalue()).decode("utf-8")


    buffered_rule_vector_matrix = BytesIO()
    rule_vector_matrix_img = rule_vector_matrix_save(non_uniform_rule_binary_list)
    rule_vector_matrix_img.save(buffered_rule_vector_matrix, format="PNG")
    img_str_rule_vector_matrix = base64.b64encode(buffered_rule_vector_matrix.getvalue()).decode("utf-8")

    return img_str_automaton_inital,img_str_automaton, img_str_combined,img_str_rule_vector_matrix


# Example usage:
# img_str_automaton_initial, img_str_automaton, img_str_combined, img_str_rule_vector_matrix = generate_and_return_image(15, 20, False, "Title", "X Axis", "Y Axis", colormap='viridis')
# print(img_str_automaton_initial, img_str_automaton, img_str_combined, img_str_rule_vector_matrix)
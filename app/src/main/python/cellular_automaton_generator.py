# experiment no. 1 and 2
# uniform ca
# taking random left,center,right postion in selection
# 1. No random initial configuration
# 2. Random initial configuration

import numpy as np
import matplotlib as mpl
import matplotlib.pyplot as plt
from PIL import Image
import base64
from matplotlib import cm
from io import BytesIO
from matplotlib.colors import Normalize
import networkx as nx

def rule_to_binary(rule_number):
    binary_string = f"{rule_number:08b}"
    return np.array([int(bit) for bit in binary_string])

def generate_initial_state(size, random):
    if random:
        return np.random.choice([0, 1], size=size)
    else:
        initial_state = np.zeros(size, dtype=int)
        initial_state[size // 2] = 1
        return initial_state

def generate_next_state(current_state, rule_binary):
    size = len(current_state)
    new_state = np.zeros(size, dtype=int)
    for i in range(size):
        left = current_state[(i - 1 + size) % size]
        center = current_state[i]
        right = current_state[(i + 1) % size]
        index = 7 - (left * 4 + center * 2 + right)
        new_state[i] = rule_binary[index]
    return new_state

def generate_cellular_automaton(rule_number, size, generations, automatoninitial):
    rule_binary = rule_to_binary(rule_number)
    automaton = np.copy(automatoninitial)
    for i in range(1, generations):
        automaton[i] = generate_next_state(automaton[i - 1], rule_binary)
    return automaton

def convert_to_image(automaton,title,X_axis,Y_axiz, colormap='binary', cell_size=10):
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
    plt.savefig(buf, format='png', bbox_inches='tight', pad_inches=0.1)
    plt.close(fig)

    buf.seek(0)
    img = Image.open(buf)
    return img
def draw_concentric_circle_graph_to_image(matrix, title, colormap, cell_size=10):
    num_rows, num_cols = matrix.shape
    G = nx.Graph()

    # Calculate the figure size to simulate cell size
    fig_width = num_cols * cell_size  # Convert to inches (DPI is 100)
    fig_height = num_rows * cell_size

    fig, ax = plt.subplots(figsize=(fig_width / 100, fig_height / 100), dpi=100)

    # Add nodes and edges for each row in the matrix
    for row in range(num_rows):
        layer_radius = row + 2
        angle_step = 2 * np.pi / num_cols
        for col in range(num_cols):
            angle = col * angle_step
            x = layer_radius * np.cos(angle)
            y = layer_radius * np.sin(angle)
            node_name = f"{row}_{col}"
            G.add_node(node_name, pos=(x, y), value=matrix[row, col])
            if col > 0:
                prev_node_name = f"{row}_{col - 1}"
                G.add_edge(node_name, prev_node_name)
            if col == num_cols - 1:
                first_node_name = f"{row}_0"
                G.add_edge(node_name, first_node_name)

    pos = nx.get_node_attributes(G, 'pos')
    # Calculate the median value
    median_value = np.median(matrix)

    # Define normalization range around the median
    vmin = median_value - np.abs(matrix - median_value).max()
    vmax = median_value + np.abs(matrix - median_value).max()

    # Normalize matrix values for colormap
    norm = Normalize(vmin=vmin, vmax=vmax)
    # norm = Normalize(vmin=matrix.min(), vmax=matrix.max())

    cmap = mpl.colormaps[colormap]
    node_colors = [cmap(norm(G.nodes[node]['value'])) for node in G.nodes]


    # Draw the graph
    nx.draw(G, pos, node_color=node_colors, with_labels=True, font_color='white', node_size=500, ax=ax)
    ax.set_title(title, fontsize=12, pad=10)
    ax.set_xlabel("Concentric Circle Graphs for CA Rows", fontsize=10)

    # Adjust the appearance of the plot
    ax.tick_params(
        axis="both", which="both", bottom=False, top=False, left=False, right=False
    )
    ax.spines["top"].set_visible(False)
    ax.spines["right"].set_visible(False)
    ax.spines["bottom"].set_visible(True)
    ax.spines["left"].set_visible(False)

    # plt.show()
    # Save the plot to a BytesIO object
    buf = BytesIO()
    plt.savefig(buf, format='png', bbox_inches='tight', pad_inches=0.1)
    plt.close(fig)

    buf.seek(0)
    img = Image.open(buf)
    return img

def cylindrical_coordinates(matrix):
    rows, cols = matrix.shape
    theta = np.linspace(0, 2 * np.pi, cols, endpoint=False)
    z = np.arange(rows)
    theta, z = np.meshgrid(theta, z)
    x = np.cos(theta)
    y = np.sin(theta)
    return x, y, z

def convert_cylindrical_to_image(automaton_matrix, title, X_axis, Y_axis, colormap="binary", cell_size=10):
    # Assume cylindrical_coordinates is defined elsewhere
    x, y, z = cylindrical_coordinates(automaton_matrix)
    generations, size = automaton_matrix.shape

    # Calculate the figure size to simulate cell size
    fig_width = size * cell_size  # Convert to inches (DPI is 100)
    fig_height = generations * cell_size

    fig = plt.figure(figsize=(fig_width / 100, fig_height / 100), dpi=100)
    ax = fig.add_subplot(111, projection="3d")

    # Calculate the median value
    median_value = np.median(automaton_matrix)

    # Define normalization range around the median
    vmin = median_value - np.abs(automaton_matrix - median_value).max()
    vmax = median_value + np.abs(automaton_matrix - median_value).max()

    # Normalize matrix values for colormap
    # norm = Normalize(vmin=vmin, vmax=vmax)
    norm = Normalize(vmin=automaton_matrix.min(), vmax=automaton_matrix.max())

    cmap = mpl.colormaps[colormap]


    # Plotting
    for i in range(automaton_matrix.shape[0]):
        ax.plot(x[i], y[i], z[i], color="gray", alpha=0.5, linewidth=2)
        ax.scatter(x[i], y[i], z[i], c=automaton_matrix[i], cmap=cmap, norm=norm, s=30, edgecolor='k')

    ax.set_title(title, fontsize=12, pad=10)
    ax.set_xlabel(X_axis, fontsize=10)
    ax.set_ylabel(X_axis, fontsize=10)
    # ax.set_zlabel(Y_axis, fontsize=10)  # Add a z-axis label if applicable

    # Hide the ticks and frame but keep the labels
    ax.tick_params(axis="both", which="both", bottom=False, top=False, left=False, right=False)
    ax.spines["top"].set_visible(False)
    ax.spines["right"].set_visible(False)
    ax.spines["bottom"].set_visible(True)
    ax.spines["left"].set_visible(True)

    # plt.show()
    # Save the plot to a BytesIO object
    buf = BytesIO()
    plt.savefig(buf, format="png", bbox_inches="tight", pad_inches=0.1)
    plt.close(fig)

    buf.seek(0)
    img = Image.open(buf)
    return img


def generate_and_return_image(rule_number, size, generations, random_initial_state,title="",X_axis="",Y_axis="", colormap='binary', cell_size=10):

    automatoninitial = np.zeros((generations, size), dtype=int)
    rule_binary = rule_to_binary(rule_number)
    automatoninitial[0] = generate_initial_state(size, random_initial_state)
    automatonfinal = generate_cellular_automaton(rule_number, size, generations, automatoninitial)

    # image1: initial image of rule image
    buffered_automaton_initial = BytesIO()
    automaton_img_initial = convert_to_image(automatoninitial, title, X_axis, Y_axis, colormap, cell_size)
    automaton_img_initial.save(buffered_automaton_initial, format="PNG")
    img_str_automaton_inital = base64.b64encode(buffered_automaton_initial.getvalue()).decode("utf-8")

    # image 2:  final image of rule image
    buffered_automaton = BytesIO()
    automaton_img_final = convert_to_image(automatonfinal, title, X_axis, Y_axis, colormap, cell_size)
    automaton_img_final.save(buffered_automaton, format="PNG")
    img_str_automaton_final = base64.b64encode(buffered_automaton.getvalue()).decode("utf-8")

    # image 3:  Concentric Circle Graphs image
    buffered_concentric_circle_image = BytesIO()
    img_concentric_circle = draw_concentric_circle_graph_to_image(automatonfinal, title,colormap,cell_size )
    img_concentric_circle.save(buffered_concentric_circle_image, format="PNG")
    img_str_concentric_circle = base64.b64encode(buffered_concentric_circle_image.getvalue()).decode("utf-8")


    # image 4:  Cylindrical Space-Time Diagram image
    buffered_cylindrical_image  = BytesIO()
    img_cylindrical = convert_cylindrical_to_image(automatonfinal, title, X_axis, Y_axis, colormap, cell_size)
    img_cylindrical.save(buffered_cylindrical_image, format="PNG")
    img_str_cylindrical = base64.b64encode(buffered_cylindrical_image.getvalue()).decode("utf-8")

    return img_str_automaton_inital,img_str_automaton_final, img_str_concentric_circle ,img_str_cylindrical


if __name__ == "__main__":
    rule_number = 30
    size = 10
    generations = 10
    random_initial_state = False
    img_str_automaton_inital,img_str_automaton_final, img_str_concentric_circle ,img_str_cylindrical = generate_and_return_image(
        rule_number,
        size,
        generations,
        random_initial_state,
        title="Sugam shaw",
        X_axis="size",
        Y_axis="generation",
        colormap="binary",
        cell_size=10,
    )

    # print("img_str_cylindrical: ", img_str_cylindrical)
    print("img_concentric_circle_str: ", img_str_concentric_circle)

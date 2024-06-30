# experiment no. 1 and 2
# uniform ca
# taking random left,center,right postion in selection
# 1. No random initial configuration
# 2. Random initial configuration

import numpy as np
import matplotlib.pyplot as plt
from PIL import Image
import base64
from io import BytesIO

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

def generate_cellular_automaton(rule_number, size, generations, random_initial_state):
    automaton = np.zeros((generations, size), dtype=int)
    rule_binary = rule_to_binary(rule_number)
    automaton[0] = generate_initial_state(size, random_initial_state)
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

def generate_and_return_image(rule_number, size, generations, random_initial_state,title="",X_axis="",Y_axiz="", colormap='binary', cell_size=10):
    automaton = generate_cellular_automaton(rule_number, size, generations, random_initial_state)
    img = convert_to_image(automaton,title,X_axis,Y_axiz, colormap, cell_size)

    buffered = BytesIO()
    img.save(buffered, format="PNG")
    img_str = base64.b64encode(buffered.getvalue()).decode("utf-8")
    return img_str

# Example usage with binary colormap and cell size
rule_number = 30
size = 100
generations = 50
random_initial_state = False
cell_size = 10

# print(generate_and_return_image(rule_number, size, generations, random_initial_state, colormap='binary', cell_size=cell_size))

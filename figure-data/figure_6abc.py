import pandas as pd
import matplotlib.pyplot as plt

def plot_position(ax, filename):
    column_names = ['CarId', 'Time', 'Position']
    A = pd.read_csv(filename, sep='\s+', names=column_names)
    veh_data = {}

    for k in range(1, 11):
        veh_data[k] = A[A['CarId'] == k][['Time', 'Position']]

    for k in range(1, 11):
        if not veh_data[k].empty:
            ax.plot(veh_data[k]['Time'], veh_data[k]['Position'], label=f'Car{k}', linestyle='-', linewidth=2)

    ax.legend(loc='upper right', bbox_to_anchor=(1.15, 1))
    ax.grid(True)
    ax.set_xlabel('Time (s)')
    ax.set_ylabel('Position (m)')
    ax.set_title('Position (fwkDEVS)', color='red')
    ax.set_ylim([0, 1000])
    ax.set_xlim([0, 250])

fig, axs = plt.subplots(1, 3, figsize=(15, 5))

plot_position(axs[0], 'small-scale/position10_3')
axs[0].set_title('Figure 6a', color='red')

plot_position(axs[1], 'small-scale/position10_7')
axs[1].set_title('Figure 6b', color='red')

plot_position(axs[2], 'small-scale/position10_9')
axs[2].set_title('Figure 6c', color='red')

plt.tight_layout()
plt.show()

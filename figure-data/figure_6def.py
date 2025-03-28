import pandas as pd
import matplotlib.pyplot as plt

def plot_position(ax, filename):
    
    B = pd.read_csv(filename, delimiter=';')
    B.columns = B.columns.str.strip()
    B.dropna(subset=['vehicle_id'], inplace=True)
    
    veh_data = {}

    for k in range(1, 11):
        veh_data[k] = B[B['vehicle_id'] == f'veh{k}'][['timestep_time', 'vehicle_pos', 'vehicle_speed']]

    for k in range(1, 11):
        if not veh_data[k].empty:
            ax.plot(veh_data[k]['timestep_time'], veh_data[k]['vehicle_pos'], label=f'Car{k}', linestyle='-', linewidth=2)

    ax.legend(loc='upper right', bbox_to_anchor=(1.15, 1))
    ax.grid(True)
    ax.set_xlabel('Time (s)')
    ax.set_ylabel('Position (m)')
    ax.set_ylim([0, 1000])
    ax.set_xlim([0, 250])
    ax.set_title('Position (SUMO)', color='blue')

# Create subplots
fig, axs = plt.subplots(1, 3, figsize=(15, 5))

plot_position(axs[0], 'small-scale/dump10_3.out.csv')
axs[0].set_title('Figure 6d', color='blue')

plot_position(axs[1], 'small-scale/dump10_7.out.csv')
axs[1].set_title('Figure 6e', color='blue')

plot_position(axs[2], 'small-scale/dump10_9.out.csv')
axs[2].set_title('Figure 6f', color='blue')

plt.tight_layout()
plt.show()

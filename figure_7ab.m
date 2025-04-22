sumo = readtable('IDM-model/output/dump10_3.out.csv');
devs_pos = readtable('DEv-CF/output/position10_3');
dev_speed = readtable('DEv-CF/output/speed10_3');

k = 9;
sumo_data{k} = sumo(matches(sumo.vehicle_id, "veh" + string(k)), ["timestep_time","vehicle_pos","vehicle_speed"]);
devs_pos_data{k} = devs_pos(eq(devs_pos.Var1,k), ["Var2","Var3"]);
devs_speed_data{k} = dev_speed(eq(devs_pos.Var1,k), ["Var2","Var3"]);

%==== Figure 7a ====

figure(1);

% Plot the main figure (figure 1)
plot(table2array(sumo_data{k}(:,"timestep_time")), table2array(sumo_data{k}(:,"vehicle_pos")),'DisplayName','IDM','LineStyle','-', 'LineWidth', 2, 'Color','blue')
hold on
plot(table2array(devs_pos_data{k}(:,"Var2")), table2array(devs_pos_data{k}(:,"Var3")),'DisplayName','DEv-CF','LineStyle','--', 'LineWidth', 2)
hold off

% Add rectangle to highlight the zoom-in area (170 to 200 seconds)
rectangle('Position', [180, 470, 15,90 ], ...
    'EdgeColor', 'r', 'LineWidth', 1, 'LineStyle', '--');
rectangle('Position', [200, 50, 115,530 ], ...
    'EdgeColor', 'k', 'LineWidth', 1, 'LineStyle', '-','FaceColor', 'w');


% Set the main plot details
legend();
grid();
xlabel('Time (s)','FontSize', 15); ylabel('Position (m)','FontSize', 15)
xlim([130 320])
set(gca, 'FontSize', 14); 

% Create a new set of axes for the inset plot (figure 2) inside figure 1
% These axes will appear within the main figure without using subplot
ax_inset = axes('Position', [0.49, 0.23, 0.35, 0.34]); % Position the inset axes inside the figure

% Plot the zoomed-in data (figure 2) in the inset
plot(table2array(sumo_data{k}(:,"timestep_time")), table2array(sumo_data{k}(:,"vehicle_pos")),'DisplayName','IDM','LineStyle','-', 'LineWidth', 2, 'Color','blue')
hold on
plot(table2array(devs_pos_data{k}(:,"Var2")), table2array(devs_pos_data{k}(:,"Var3")),'DisplayName','DEv-CF','LineStyle','--', 'LineWidth', 2)
hold off

% Set details for the zoomed-in plot
legend('show', 'Location', 'northwest');
grid();
xlim([180 190]);  % Zoom in to the region of interest
ylim([450,580]);  % Adjust Y-axis for the zoomed-in region

% Add the arrow
annotation('arrow',  [0.36, .41], [.5 0.37], 'Color', 'k', 'LineWidth', 1);

% saveas(gcf, 'compare_idm_devs.eps', 'epsc');  % 'epsc' saves in color EPS format



%==== Figure 7b ====

figure(3)
plot(table2array(sumo_data{k}(:,"timestep_time")), table2array(sumo_data{k}(:,"vehicle_speed")),'DisplayName','IDM','LineStyle','-', 'LineWidth', 2, 'Color','blue')
hold on
stairs(table2array(devs_speed_data{k}(:,"Var2")), table2array(devs_speed_data{k}(:,"Var3")),'DisplayName','DEv-CF','LineStyle','--', 'LineWidth', 2)
hold off
legend();
grid();
xlabel('Time (s)','FontSize', 15); ylabel('Speed (m/s)','FontSize', 15)
xlim([130 250])
ylim([7 12])
set(gca, 'FontSize', 14); 
% saveas(gcf, 'Speed_idm_devs.eps', 'epsc');
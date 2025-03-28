SUMO = readtable('medium-scale/summary10000_1.out.csv')
time_SUMO = table2array(SUMO(:,"step_time"))/3600
count_SUMO = table2array(SUMO(:,"step_running"))

DEVS = readtable('medium-scale/numCar10000_1')
time_DEVS = table2array(DEVS(:,"Var1"))/3600
count_DEVS = table2array(DEVS(:,"Var2"))
    
figure
chart = stairs(time_SUMO, count_SUMO, 'LineWidth', 2, 'DisplayName','IDM')
hold on
chart = stairs(time_DEVS, count_DEVS, 'LineWidth', 2, 'DisplayName','DEv-CF')
hold off
xlim([0 24])
xticks(0:4:24)
grid();
legend();
xlabel('Time (h)'); ylabel('Number of cars')
% title('Numbers of cars running (SUMO)','Color', 'blue')
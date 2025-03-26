plot_position('small-scale/dump10_3.out.csv');
plot_position('small-scale/dump10_7.out.csv');
plot_position('small-scale/dump10_9.out.csv');

function plot_position(filename)
    B = readtable(filename)

    for k = 1:10
        veh_data{k} = B(matches(B.vehicle_id, "veh" + string(k)), ["timestep_time","vehicle_pos","vehicle_speed"])
    end

    figure
    for k = 1:10
        plot(table2array(veh_data{k}(:,"timestep_time")), table2array(veh_data{k}(:,"vehicle_pos")),'DisplayName',['Car' num2str(k)],'LineStyle','-', 'LineWidth', 2)
        hold on
    end
    hold off
    legend('Location','northeastoutside')
    grid();
    xlabel('Time (s)'); ylabel('Position (m)')
    title('Position (SUMO)','Color', 'blue')
    xlim([0 250])

end

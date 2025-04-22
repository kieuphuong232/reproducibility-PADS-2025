plot_position('DEv-CF/output/position10_3');
plot_position('DEv-CF/output/position10_7');
plot_position('DEv-CF/output/position10_9');

function plot_position(filename)  
    A = readtable(filename)
    
    for k = 1:10
        veh_data{k} = A(eq(A.Var1,k), ["Var2","Var3"])
    end

    figure
    for k = 1:10
        plot(table2array(veh_data{k}(:,"Var2")), table2array(veh_data{k}(:,"Var3")),'DisplayName',['Car' num2str(k)],'LineStyle','-', 'LineWidth', 2)
        hold on
    end
    hold off
    legend('Location','northeastoutside')
    grid()
    xlabel('Time (s)'); ylabel('Position (m)')
    title('Position (fwkDEVS)','Color','red')
    ylim([0 1000])
    xlim([0 250])
    
end
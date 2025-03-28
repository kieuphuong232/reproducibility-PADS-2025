sumo = [545.14	1231.46	1918.82	2611.42	3252.70	6450.62]
DEVS = [268.47  457.14	637.12	847.26	1081.60	2265.26]
mean = (sumo/DEVS)
num = [100000	200000	300000	400000	500000	1000000]
plot([10000	num], [31.38 sumo],'DisplayName',"SUMO",'Marker','o','LineWidth',2,'MarkerSize',2)
text(num, sumo, string(sumo),'HorizontalAlignment','right','VerticalAlignment','baseline')
hold on 
plot([10000	num], [14.82 DEVS], 'DisplayName',"fwkDEVS",'Marker','o','LineWidth',2,'MarkerSize',2)
text(num, DEVS, string(DEVS),'HorizontalAlignment','left','VerticalAlignment','cap')
hold off
legend(); grid()
xscale log
% title('Avarage Execution Time')
xlabel('Number of vehicles'); ylabel('Time (s)')
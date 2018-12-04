

    var worldMapContainer = document.getElementById('chartLine');
    var resizeWorldMapContainer = function () {
        worldMapContainer.style.width = window.innerWidth + 'px';
        worldMapContainer.style.height = window.innerHeight + 'px';    
    };
    resizeWorldMapContainer();
    var myChart = echarts.init(worldMapContainer);
var option = {
   title: {
       text: "报警趋势",
	    textStyle:{
        color:'#fff',
　　　　 fontSize:12,
	   
    },

       x: "left",

	    
   },
	tooltip: {
		trigger: 'axis',
		axisPointer: {
			type: 'line',
			lineStyle: {
				color: '#ced4da',
				width: 2,
				type: ' dashed'
			}
		}
	},

	grid: {
		top: '15%',
		left: '2%',
		right: '10%',
		bottom: '10%',
		containLabel: true
	},
	xAxis: [{
		type: 'category',
		boundaryGap: false,
              axisLine: {
                    lineStyle: {
                        type: 'solid',
                        color: '#eaedf2',
                        width:'1'
                    }
                },
		        axisLabel: {
                    textStyle: {
                        color: '#eaedf2',
 
                    }
                },
		splitLine: {
			show: false
		},
	

	
		data: function() {
			var list = [];
			for(var i = 10; i <= 18; i++) {
				if(i <= 12) {
					list.push(+i + '-01');
				} else {
					list.push(+(i - 12) + '-01');
				}
			}
			return list;
		}()
	}],

	yAxis: [{
		type: 'value',
			splitLine: {
			 show: true,
                lineStyle: {
                    color: ['#666']
                }
		},
		      axisLabel: {
                    textStyle: {
                        color: '#eaedf2',
 
                    }
                },
		axisLine: {
			show: false
		},
		axisTick: {
			show: false
		}
	
	}],

	series: [{

		type: 'line',
		name: '报警次数',
		color: ['#01D5E1'],
		symbol: 'none',
		data: [8, 20, 45, 78, 88, 35, 65, 25, 94]
	}]
};

myChart.setOption(option);
  window.onresize = function () {
      
        resizeWorldMapContainer();
        myChart.resize();
    };
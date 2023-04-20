var chart = {

	loadChartData1 : function() {

		var formattedchartListArray = [];

		$.ajax({

			async : false,

			url : "MostVisitedChartServlet",

			dataType : "json",

			success : function(chartJsonData) {

				console.log(chartJsonData);

				$.each(chartJsonData, function(index, aData) {

					formattedchartListArray.push([ aData.views1,
							aData.question1, aData.views2, aData.question2,
							aData.views3, aData.question3, aData.views4,
							aData.question4, aData.views5, aData.question5, ]);
				});
			}
		});
		return formattedchartListArray;
	},

	loadChartData2 : function() {

		var formattedchartListArray = [];

		$.ajax({

			async : false,

			url : "MostCommChartServlet",

			dataType : "json",

			success : function(chartJsonData) {

				console.log(chartJsonData);

				$.each(chartJsonData, function(index, aData) {

					formattedchartListArray.push([ aData.sum1, aData.question1,
							aData.sum2, aData.question2, aData.sum3,
							aData.question3, aData.sum4, aData.question4,
							aData.sum5, aData.question5, aData.sum6,
							aData.question6, aData.sum7, aData.question7,
							aData.sum8, aData.question8 ]);
				});
			}
		});
		return formattedchartListArray;
	},

	loadChartData3 : function() {

		var formattedchartListArray = [];

		$.ajax({

			async : false,

			url : "MostPopCatChartServlet",

			dataType : "json",

			success : function(chartJsonData) {

				console.log(chartJsonData);

				$.each(chartJsonData, function(index, aData) {

					formattedchartListArray.push([ aData.sum1, aData.cat1,
							aData.sum2, aData.cat2, aData.sum3, aData.cat3,
							aData.sum4, aData.cat4, aData.sum5, aData.cat5 ]);
				});
			}
		});
		return formattedchartListArray;
	},

	createChartData1 : function(jsonData) {

		console.log(jsonData);
		// console.log(jsonData[0][3]);
		// console.log(jsonData[0])

		return {

			labels : [ jsonData[0][1], jsonData[0][3], jsonData[0][5],
					jsonData[0][7], jsonData[0][9] ],

			datasets : [ {
				backgroundColor : [ '#ebf4f6', '#bdeaee',
						'#76b4bd', '#58668b', '#5e5656',
						],

				data : [ jsonData[0][0], jsonData[0][2], jsonData[0][4],
						jsonData[0][6], jsonData[0][8] ]
			} ]
		};
	},

	createChartData2 : function(jsonData) {

		console.log(jsonData);
		// console.log(jsonData[0][3]);
		// console.log(jsonData[0])

		return {

			labels : [ jsonData[0][1], jsonData[0][3], jsonData[0][5],
					jsonData[0][7], jsonData[0][9], jsonData[0][11],
					jsonData[0][13], jsonData[0][15] ],

			datasets : [ {
				backgroundColor : [ '#fe8a71',
						' #f6cd61', '#3da4ab',
						'#4a4e4d', 'rgba(120, 111, 166,1.0)',
						'rgba(99, 205, 218,1.0)', 'rgba(243, 166, 131,1.0)',
						'rgba(87, 75, 144,1.0)' ],

				data : [ jsonData[0][0], jsonData[0][2], jsonData[0][4],
						jsonData[0][6], jsonData[0][8], jsonData[0][10],
						jsonData[0][12], jsonData[0][14] ]
			} ]
		};
	},

	createChartData3 : function(jsonData) {

		console.log(jsonData);
		// console.log(jsonData[0][3]);
		// console.log(jsonData[0])

		return {

			labels : [ jsonData[0][1], jsonData[0][3], jsonData[0][5],
					jsonData[0][7], jsonData[0][9] ],

			datasets : [ {
				data : [ jsonData[0][0], jsonData[0][2], jsonData[0][4],
						jsonData[0][6], jsonData[0][8] ],
				pointBackgroundColor : [ '#2e003e',
						'#3d2352', '#3d1e6d',
						'#8874a3', '#e4dcf1' ],
				pointRadius : 10,
				fill : false,
				showLine : false
			} ]
		};
	},

	renderChart1 : function(chartData) {

		var context2D = document.getElementById("mostVisited").getContext("2d"),

		myChart = new Chart(context2D, {
			type : "bar",
			data : chartData,
			options : {
				plugins : {
					title : {
						display : true,
						text : 'Most visited questions'
					},
					legend : {
						display : false
					}
				},
				scales : {
					x : {
						display : false

					},
					y : {
						title : {
							display : true,
							text : 'Number of views'
						}
					}
				}
			}
		});

		return myChart;
	},

	renderChart2 : function(chartData) {

		var context2D = document.getElementById("mostCommented").getContext(
				"2d"),

		myChart = new Chart(
				context2D,
				{
					type : "doughnut",
					data : chartData,
					options : {
						plugins : {
							title : {
								display : true,
								text : 'Most commented questions (sum of answers and comments)'
							},
							legend : {
								display : false
							}
						},
						scales : {
							x : {
								display : false

							},
							y : {
								display : false
							}
						}
					}
				});

		return myChart;
	},

	renderChart3 : function(chartData) {

		var context2D = document.getElementById("mostPopCat").getContext("2d"),

		myChart = new Chart(context2D, {
			type : 'line',
			data : chartData,
			options : {
				plugins : {
					legend : {
						display : false,
					},
					title : {
						display : true,
						text : 'Most popular categories'
					}
				},
				scales : {
					y : {
						title : {
							display : true,
							text : 'Number of questions'
						},
						ticks : {
							precision : 0,
						},
						beginAtZero: true,
			            min: 0
					}
				}
			},
		});

		return myChart;
	},

	initChart1 : function() {

		var chartData = chart.loadChartData1();

		chartData = chart.createChartData1(chartData);

		chartObj = chart.renderChart1(chartData);

	},

	initChart2 : function() {

		var chartData = chart.loadChartData2();

		chartData = chart.createChartData2(chartData);

		chartObj = chart.renderChart2(chartData);

	},

	initChart3 : function() {

		var chartData = chart.loadChartData3();

		chartData = chart.createChartData3(chartData);

		chartObj = chart.renderChart3(chartData);

	}
};

$(document).ready(function() {

	chart.initChart1();
	chart.initChart2();
	chart.initChart3();
});
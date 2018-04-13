	$(document).ready(function() {
		function jsUcfirst(string) 
		{
		    return string.charAt(0).toUpperCase() + string.slice(1);
		}
		var totalTimesTask1 = $("#totalTimesAT1").val();
		var wrongTimesTask1 = $("#wrongTimesAT1").val();
		var arrAT1Name = $(".arrAT1Name");
		var arrAT1WrongTimes = $(".arrAT1WrongTimes");
		var arrAT1TotalTimes = $(".arrAT1TotalTimes");
		var totalTimesTask2 = $("#totalTimesAT2").val();
		var wrongTimesTask2 = $("#wrongTimesAT2").val();
		var arrAT2Name = $(".arrAT2Name");
		var arrAT2WrongTimes = $(".arrAT2WrongTimes");
		var arrAT2TotalTimes = $(".arrAT2TotalTimes");
		if(arrAT1Name.length===0) {
			$("#chartContainer1").text("There aren't any sentence in lesson!");
		} else{
			<!-- Task1 -->
			var myDataCorrect = new Array(arrAT1Name.length);
			var myDataIncorrect = new Array(arrAT1Name.length);
			for(var i = 0; i< arrAT1Name.length;i++){
				myDataIncorrect[i] = {
						y : arrAT1WrongTimes.eq(i).val(),
						label : jsUcfirst(arrAT1Name.eq(i).val())
					};
				myDataCorrect[i] = {
						y : arrAT1TotalTimes.eq(i).val() - arrAT1WrongTimes.eq(i).val(),
						label : jsUcfirst(arrAT1Name.eq(i).val())
					};
				
			}
			var chart1 = new CanvasJS.Chart("chartContainer1", {
				animationEnabled: true,
				theme: "light2", //"light1", "dark1", "dark2"
				title:{
					text: "Task 1 Overview - Wrong Answers: "+wrongTimesTask1+"/" + totalTimesTask1             
				},
				axisX:{
					labelMaxWidth: 100,
					labelWrap: false
				},
				axisY:{
					interval: 10,
					suffix: "%"
				},
				toolTip:{
					shared: true
				},
				data:[{
					type: "stackedBar100",
					toolTipContent: "{label}<br><b>{name}:</b> {y}",
					showInLegend: true, 
					name: "Incorrect",
					color:"#C82E31",
					dataPoints: myDataIncorrect
					},
					{
						type: "stackedBar100",
						toolTipContent: "<b>{name}:</b> {y}",
						showInLegend: true, 
						name: "Correct",
						color: "#00AE72",
						dataPoints: myDataCorrect
					}]
			});
			chart1.render();
		}
		
		
		if(arrAT2Name.length===0) {
			$("#chartContainer2").text("There aren't any word in lesson!");
		} else{
			var myDataTask2 = new Array(arrAT2Name.length); 
			
			for(var i = 0; i< arrAT2Name.length;i++){
				myDataTask2[i] = {
						y : arrAT2WrongTimes.eq(i).val(),
						name : jsUcfirst(arrAT2Name.eq(i).val())
					};
			}
			<!-- Task2 -->
			var chart2 = new CanvasJS.Chart("chartContainer2", {
				theme : "light2",
				exportFileName : "Task2 Chart",
				exportEnabled : false,
				animationEnabled : true,
				title : {
					text : "Task 2 Overview - Wrong Answers: "+wrongTimesTask2+"/" + totalTimesTask2
				},
				legend : {
					cursor : "pointer",
					itemclick : explodePie
				},
				data : [ {
					type : "doughnut",
					innerRadius : 70,
					showInLegend : true,
					toolTipContent : "<b>{name}</b>: Wrong times {y}",
					indexLabel : "{name} - {y}",
					dataPoints : myDataTask2
				} ]
			});
			chart2.render();
		}

		function explodePie(e) {
			if (typeof (e.dataSeries.dataPoints[e.dataPointIndex].exploded) === "undefined"
					|| !e.dataSeries.dataPoints[e.dataPointIndex].exploded) {
				e.dataSeries.dataPoints[e.dataPointIndex].exploded = true;
			} else {
				e.dataSeries.dataPoints[e.dataPointIndex].exploded = false;
			}
			if(chart2 != undefined)
				e.chart2.render();
		}

	});
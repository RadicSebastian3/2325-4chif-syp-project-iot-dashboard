const date = document.getElementById("date");
const submitButton = document.getElementById("submitBtn");

submitButton.addEventListener("click", () => {
    const dateValueMap = fetchData(date.value);

    const chart = new CanvasJS.Chart('chartContainer', {
        axisX: {
            valueFormatString: 'HH:mm',
            interval: 1,
            labelAngle: -50,
            title: 'Time HH:mm'
        },
        axisY: {
            title: 'W'
        },
        data: [{
            type: 'line',
            dataPoints: Array.from(dateValueMap, ([date, value]) => ({
                x: date,
                y: value
            }))
        }]
    });
    chart.render();
});


function fetchData(dayIso){
    //TODO:
    //fetch the data from the quarkus application from the specific day from 0:00 am to 11:59pm
    //@param dayIso: the day where we want to get the data
    //@returns a key-value collection (e.g. Map) with the time as key and the watt-value as value
    return fillExampleDataToMap();
}

function fillExampleDataToMap(){
    const map = new Map();
    map.set(new Date(2023,9,1,0,0,5), 70);
    map.set(new Date(2023,9,1,0,45,5), 30);
    map.set(new Date(2023,9,1,2,0,5), 85);
    map.set(new Date(2023,9,1,3,0,5), 70);
    map.set(new Date(2023,9,1,4,45,5), 30);
    map.set(new Date(2023,9,1,6,23,5), 85)
    map.set(new Date(2023,9,1,20,23,5), 1);
    return map;
}

function averageOfCollection(collection){
    let sum = 0;

    for(var item of collection){
        sum += item;
    }

    return sum/collection.size;
}





/*fetch('https://localhost:8080/device/data')
.then(response => response.json())
.then(data => {
    createChart(data, 'monthlyChart', 'Monthly');
    createChart(data, 'dailyChart', 'Daily');
})

function createChart(data, chartId, title) {
    const ctx = document.getElementById(chartId).getContext('2d');
    new Chart(ctx, {
        type: 'line',
        data: {
            labels: data.labels,
            datasets: data.datasets
        },
        options: {
            title: {
                display: true,
                text: title
            },
            scales: {
                yAxes: [{
                    ticks: {
                        beginAtZero: true
                    }
                }]
            }
        }
    });
}*/
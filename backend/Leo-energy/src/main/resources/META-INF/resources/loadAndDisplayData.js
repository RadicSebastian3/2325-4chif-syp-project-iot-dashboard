fetch('https://localhost:8080/device/data')
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
}
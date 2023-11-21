window.onload = fetchDeviceData;
let jsonData = null;

function fetchDeviceData(){
    fetch('/device/data')
        .then(response => {
            if (!response.ok) {
                throw new Error('Network response was not ok');
            }
            return response.json();
        })
        .then(json => {
            jsonData = json;
            createDevicesTable(json.devices);
            createValuesTable(json.values);
            createValueTypesTable(json.valueTypes);
            createUnitsTable(json.units);
        })
        .catch(error => console.error('Error:', error));
}

function createDevicesTable(devices) {
    const devicesTable = document.getElementById('devicesTable').getElementsByTagName('tbody')[0];

    for (const device of devices) {
        const row = devicesTable.insertRow();
        row.insertCell(0).textContent = device.id;
        row.insertCell(1).textContent = device.name;
        row.insertCell(2).textContent = device.site;
    }
}

function createValuesTable(values) {
    const valuesTable = document.getElementById('valuesTable').getElementsByTagName('tbody')[0];
    valuesTable.innerHTML = "";

    for (const value of values) {
        const row = valuesTable.insertRow();
        row.insertCell(0).textContent = value.id;
        row.insertCell(1).textContent = value.time;
        row.insertCell(2).textContent = value.value;
        row.insertCell(3).textContent = value.valueType.description;
        row.insertCell(4).textContent = value.valueType.unit.name;
    }
}

function createValueTypesTable(valueTypes) {
    const valueTypesTable = document.getElementById('valueTypesTable').getElementsByTagName('tbody')[0];

    for (const valueType of valueTypes) {
        const row = valueTypesTable.insertRow();
        row.insertCell(0).textContent = valueType.id;
        row.insertCell(1).textContent = valueType.description;
        row.insertCell(2).textContent = valueType.presistValues;
        row.insertCell(3).textContent = valueType.sentomqtt;
        row.insertCell(4).textContent = valueType.unit.id;
    }
}

function createUnitsTable(units) {
    const unitsTable = document.getElementById('unitsTable').getElementsByTagName('tbody')[0];

    for (const unit of units) {
        const row = unitsTable.insertRow();
        row.insertCell(0).textContent = unit.id;
        row.insertCell(1).textContent = unit.name;
    }
}

document.getElementById('sortValues').addEventListener('change', function () {
    const sortBy = this.value;
    sortValuesTable(sortBy);
});

function sortValuesTable(sortBy) {
    jsonData.values.sort((a, b) => {

        let aValue, bValue;

        if (sortBy === 'valueType') {
            aValue = a.valueType.description;
            bValue = b.valueType.description;
        } else if (sortBy === 'unit') {
            aValue = a.valueType.unit.name;
            bValue = b.valueType.unit.name;
        } else {
            aValue = a[sortBy];
            bValue = b[sortBy];
        }

        if (aValue < bValue) { return -1; }
        if (aValue > bValue) { return 1; }
        return 0;
    });

    createValuesTable(jsonData.values);
}
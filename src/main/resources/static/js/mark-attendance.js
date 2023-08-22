function updatePunchInTime() {
    const date = new Date();
    var newPunchTime = date;
    document.getElementById("punch-in-time").innerText = newPunchTime;
}
function updatePunchOutTime() {
    const date = new Date();
    var newPunchTime = date;
    document.getElementById("punch-out-time").innerText = newPunchTime;
}

function myClock() {
    setTimeout(function() {
        const d = new Date();
        const n = d.toLocaleTimeString();
        document.getElementById("time").innerHTML = n;
        myClock();
    }, 1000)
}
myClock();
$(document).ready(function() {
    $('.select2-hidden-accessible').select2();
});
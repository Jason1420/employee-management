function momodalAttendance(checkInTime, checkOutTime, punchHour) {
    document.getElementById("checkInTime").innerText = checkInTime;
    document.getElementById("checkOutTime").innerText = checkOutTime;
    document.getElementById("punchHour").innerText = punchHour;
    document.getElementById("nenmodal-attendance").classList.toggle("active");
}

function momodal() {
    document.getElementById("nenmodal-1").classList.toggle("active");
}

function momodalNewEmployee() {
    document.getElementById("nenmodalemp").classList.toggle("active");
}

function momodalExport() {
    document.getElementById("nenmodalexp").classList.toggle("active");
}

function momodal2() {
    document.getElementById("nenmodal-2").classList.toggle("active");
}

function momodal(employeeId) {
    var popupId = 'nenmodal-' + employeeId;
    document.getElementById(popupId).classList.toggle("active");
}

function momodalUser(userId) {
    var popupId = 'nenmodalUser-' + userId;
    document.getElementById(popupId).classList.toggle("active");
}

function momodalUserReset(userId) {
    var popupId = 'nenmodalUserReset-' + userId;
    document.getElementById(popupId).classList.toggle("active");
}

function momodalUserEnable(userId) {
    var popupId = 'nenmodalUserEnable-' + userId;
    document.getElementById(popupId).classList.toggle("active");
}

function momodalUserDisable(userId) {
    var popupId = 'nenmodalUserDisable-' + userId;
    document.getElementById(popupId).classList.toggle("active");
}

function momodalUserLock(userId) {
    var popupId = 'nenmodalUserLock-' + userId;
    document.getElementById(popupId).classList.toggle("active");
}

function momodalUserUnlock(userId) {
    var popupId = 'nenmodalUserUnlock-' + userId;
    document.getElementById(popupId).classList.toggle("active");
}
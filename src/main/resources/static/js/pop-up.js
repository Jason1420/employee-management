function momodal(checkInTime,checkOutTime,punchHour) {
    var punchHourStr = punchHour.toString();
    punchHourStr = punchHourStr.replace('x', '');
    var str = (punchHourStr.length >= 4) ? punchHourStr.substring(0, 4) : punchHourStr;
    document.getElementById("checkInTime").innerText = checkInTime;
    document.getElementById("checkOutTime").innerText = checkOutTime;
    document.getElementById("punchHour").innerText = punchHour;
    document.getElementById("nenmodal-1").classList.toggle("active");
}
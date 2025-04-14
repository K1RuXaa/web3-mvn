var r = null;

function handleSpinnerChange() {
    var spinnerValue = document.getElementById("formSubmit:R_spinner_input").value;
    console.log("Selected value from spinner: " + spinnerValue);
    r = parseFloat(spinnerValue.replace(",", "."));
    drawGraphic()
}

function handleCheckboxSelection(checkbox) {
    var checkboxes = document.querySelectorAll('input[type="checkbox"][name^="formSubmit:x"]');
    checkboxes.forEach(function(cb) {
        if (cb !== checkbox) {
            cb.checked = false;
        }
    });
}

function drawGraphic() {
    const canvas = document.getElementById('graph');
    const ctx = canvas.getContext('2d');
    console.log(12)
    ctx.clearRect(0, 0, canvas.width, canvas.height);
    ctx.strokeStyle = "black";
    ctx.lineWidth = "2";


    const scale = (Math.min(canvas.width, canvas.height) / 3);
    const xCenter = canvas.width / 2;
    const yCenter = canvas.height / 2;

    console.log(xCenter, yCenter, scale);

    // Рисуем четверть круга в четвертой четверти
    ctx.beginPath();
    ctx.arc(xCenter, yCenter, scale / 2, 0, Math.PI / 2, false);
    ctx.lineTo(xCenter, yCenter);
    ctx.closePath();
    ctx.fillStyle = "blue";
    ctx.fill();

// Рисуем прямоугольник в третьей четверти
    ctx.fillRect(xCenter - scale / 2, yCenter, scale / 2, scale);

// Рисуем треугольник в первой четверти
    ctx.beginPath();
    ctx.moveTo(xCenter, yCenter - scale / 2); // Верхний угол
    ctx.lineTo(xCenter + scale, yCenter); // Нижний правый угол
    ctx.lineTo(xCenter, yCenter); // Центр координат
    ctx.closePath();
    ctx.fill();

// Оси координат
    ctx.beginPath();
    ctx.moveTo(0, yCenter);
    ctx.lineTo(canvas.width, yCenter);
    ctx.stroke();

    ctx.beginPath();
    ctx.moveTo(xCenter, 0);
    ctx.lineTo(xCenter, canvas.height);
    ctx.stroke();

// Стрелка оси X
    ctx.moveTo(canvas.width - 10, yCenter - 5);
    ctx.lineTo(canvas.width, yCenter);
    ctx.lineTo(canvas.width - 10, yCenter + 5);
    ctx.fillStyle = "black";
    ctx.fill();
    ctx.closePath();

// Стрелка оси Y
    ctx.moveTo(xCenter - 5, 10);
    ctx.lineTo(xCenter, 0);
    ctx.lineTo(xCenter + 5, 10);
    ctx.fillStyle = "black";
    ctx.fill();
    ctx.closePath();

// Подписи осей
    ctx.textAlign = "left";
    ctx.textBaseline = "middle";
    ctx.fillStyle = "black";
    ctx.font = "14px Arial";
    ctx.fillText("X", canvas.width - 14, yCenter + 19);
    ctx.fillText("Y", xCenter + 10, 14);


    if (r == null) {
        ctx.fillText("-R", xCenter + 7, yCenter + scale);
        ctx.fillText("R", xCenter + 7, yCenter - scale);
        ctx.fillText("-R/2", xCenter + 7, yCenter + scale / 2);
        ctx.fillText("R/2", xCenter + 7, yCenter - scale / 2);

        ctx.fillText("-R", xCenter - scale, yCenter - 10);
        ctx.fillText("R", xCenter + scale, yCenter - 10);
        ctx.fillText("-R/2", xCenter - scale / 2, yCenter - 10);
        ctx.fillText("R/2", xCenter + scale / 2, yCenter - 10);
    } else {
        ctx.fillText(-r, xCenter + 7, yCenter + scale);
        ctx.fillText(r, xCenter + 7, yCenter - scale);
        ctx.fillText(-r / 2, xCenter + 7, yCenter + scale / 2);
        ctx.fillText(r / 2, xCenter + 7, yCenter - scale / 2);

        ctx.fillText(-r, xCenter - scale, yCenter + 10);
        ctx.fillText(r, xCenter + scale, yCenter + 10);
        ctx.fillText(-r / 2, xCenter - scale / 2, yCenter + 10);
        ctx.fillText(r / 2, xCenter + scale / 2, yCenter + 10);
        console.log(results);
        var dots = results;
        for (let i = 0; i < dots.length; i++) {
            if (dots[i].r === r){
                drawPoint(ctx, scale, dots[i].x, dots[i].y, r, dots[i].hit ? "green" : "red", xCenter, yCenter);
            } else {
                drawPoint(ctx, scale, dots[i].x, dots[i].y, r, "black", xCenter, yCenter);
            }
        }
    }
}

function drawPoint(ctx, scale, x, y, r, color, xCenter, yCenter){
    x = parseFloat(x);
    y = parseFloat(y);

    ctx.fillStyle = color;
    if (!isNaN(r)) {
        let scaleX = xCenter + (x * scale) / r;
        let scaleY = yCenter - (y * scale) / r;
        ctx.beginPath();
        ctx.arc(scaleX, scaleY, 5, 0, Math.PI * 2);
        ctx.fill();
    }
}

function checkR(r) {
    return ((r <= 3) && (r >= 1));
}

function checkX(x) {
    return ((x <= 5) && (x >= -3));
}

function checkY(y) {
    return ((y <= 3) && (y >= -5));
}

function updateSubmit() {
    let x = document.querySelector('input[name="formSubmit:xValue"]:checked').value;

    let y = document.getElementById('formSubmit:Y').value;

    let r = document.getElementById('formSubmit:R_spinner_input').value;

    r = parseFloat(r.replace(",", "."));

    console.log("x = ", x, "y = ", y, "r = ", r);

    document.getElementById("formGraph:x").value = x;
    document.getElementById("formGraph:y").value = y;
    document.getElementById("formGraph:r").value = r;
    submitGraphData();
}

document.addEventListener('DOMContentLoaded', function () {
    const canvas = document.getElementById('graph');
    if (!canvas) {
        console.error("Элемент с ID 'graph' не найден!");
        return;
    }

    const scale = (Math.min(canvas.width, canvas.height) / 3);
    const xCenter = canvas.width / 2;
    const yCenter = canvas.height / 2;

    drawGraphic();

    canvas.addEventListener("click", function (event) {
        if (r == null){
            invalidR();
        } else {
            let curr_x = event.offsetX;
            let curr_y = event.offsetY;
            curr_x -= xCenter;
            curr_y -= yCenter;
            curr_x /= scale;
            curr_y /= scale;
            let x = curr_x * r;
            y = -curr_y * r;

            console.log("point");
            console.log(x, y, r);

            document.getElementById("formGraph:x").value = x;
            document.getElementById("formGraph:y").value = y;
            document.getElementById("formGraph:r").value = r;

            submitGraphData();
        }
    });
});


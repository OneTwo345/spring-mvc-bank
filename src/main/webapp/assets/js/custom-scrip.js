var notification = document.getElementById("notification");

if (notification) {
    setTimeout(function () {
        notification.style.display = "none";
    }, 4000);
}

function confirmDelete() {
    return confirm("Bạn có chắc chắn muốn xóa?");
}

const totalInput = document.getElementById('total');

const transferAmountInput = document.getElementById('transferAmount');

transferAmountInput.addEventListener('input', function() {
    const transferAmount = parseFloat(transferAmountInput.value) || 0;
    const total = (transferAmount * 1.1).toFixed(3);
    totalInput.value = total;
});

$(document).ready(function () {
    // Lấy ID từ URL
    const urlParams = new URLSearchParams(window.location.search);
    const transactionId = urlParams.get("id");

    if (transactionId) {
        $.ajax({
            url: `http://localhost:8080/api/transactions/${transactionId}`,
            method: "GET",
            success: function (transaction) {
                $("#transactionId").text(transaction.id);
                $("#transactionCode").text(transaction.transactionId);
                $("#transactionDate").text(transaction.transactionDate);
                $("#transactionType").text(transaction.serviceType);
                $("#transactionPrice").text(transaction.unitPrice.toLocaleString());
                $("#transactionArea").text(transaction.area);
                $("#transactionCustomer").text(transaction.customer.customerName);
            },
            error: function () {
                alert("Failed to load transaction details!");
            }
        });
    } else {
        alert("Transaction ID not found!");
    }
});

$(document).ready(function () {
    loadCustomers(); // Gọi hàm load danh sách khách hàng ngay khi trang tải
});

$("#addTransactionForm").submit(function (event) {
    event.preventDefault();

    $(".error-message").remove(); // Xóa lỗi cũ

    let transactionId = $("#transactionId").val().trim();
    let transactionDate = $("#transactionDate").val();
    let serviceType = $("#serviceType").val().trim();
    let unitPrice = parseFloat($("#unitPrice").val());
    let area = parseFloat($("#area").val());
    let customerId = $("#customerId").val();

    // Kiểm tra Transaction ID
    if (!/^MGD-\d{4}$/.test(transactionId)) {
        displayError("#transactionId", "Transaction ID must follow the format MGD-XXXX (X = 0-9)");
        return;
    }


    // Kiểm tra Service Type
    if (!["Land", "House and Land"].includes(serviceType)) {
        displayError("#serviceType", "Service type must be either 'Land' or 'House and Land'");
        return;
    }

    // Kiểm tra Unit Price
    if (isNaN(unitPrice) || unitPrice < 500000) {
        displayError("#unitPrice", "Unit price must be greater than 500,000 VND");
        return;
    }

    // Kiểm tra Area
    if (isNaN(area) || area < 20) {
        displayError("#area", "Area must be greater than 20 m²");
        return;
    }

    // Kiểm tra Customer ID
    if (!customerId) {
        displayError("#customerId", "Please select a customer");
        return;
    }

    // Nếu dữ liệu hợp lệ, gửi lên server
    let transactionData = {
        transactionId: transactionId,
        transactionDate: transactionDate,
        serviceType: serviceType,
        unitPrice: unitPrice,
        area: area,
        customer: { id: parseInt(customerId) }
    };

    $.ajax({
        url: "http://localhost:8080/api/transactions",
        method: "POST",
        contentType: "application/json",
        data: JSON.stringify(transactionData),
        success: function () {
            alert("Transaction added successfully!");
            window.location.href = "list.html";
        },
        error: function (xhr) {
            console.error("Error:", xhr.responseText);
            try {
                let errorResponse = JSON.parse(xhr.responseText);
                if (errorResponse.errors && errorResponse.errors.length > 0) {
                    errorResponse.errors.forEach(error => {
                        let [field, message] = error.split(": ");
                        displayError(`#${field}`, message);
                    });
                } else {
                    alert("Có lỗi xảy ra khi thêm giao dịch.");
                }
            } catch (e) {
                alert("Lỗi không xác định!");
            }
        }
    });
});

// Hàm hiển thị lỗi
function displayError(selector, message) {
    $(selector).after(`<p class="error-message" style="color:red;">${message}</p>`);
}
function loadCustomers() {
    $.ajax({
        url: "http://localhost:8080/api/customers",
        method: "GET",
        success: function (customers) {
            console.log("Customers loaded:", customers); // Debug xem API có trả về không
            let customerDropdown = $("#customerId");
            customerDropdown.empty();
            customerDropdown.append(`<option value="">Select a customer</option>`);
            customers.forEach(customer => {
                customerDropdown.append(`<option value="${customer.id}">${customer.customerName}</option>`);
            });
        },
        error: function (xhr, status, error) {
            console.error("Error loading customers:", xhr.responseText);
            alert("Failed to load customers!");
        }
    });
}

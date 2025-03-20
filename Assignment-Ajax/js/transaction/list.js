let currentPage = 0;
let pageSize = 5;
let totalPages = 1;
let searchQuery = "";

// Load danh sách giao dịch dựa trên bộ lọc
function loadTransactions() {
    let url = searchQuery
        ? `http://localhost:8080/api/transactions/search?search=${searchQuery}&page=${currentPage}&size=${pageSize}`
        : `http://localhost:8080/api/transactions?page=${currentPage}&size=${pageSize}`;

    console.log("Fetching data from:", url); // Kiểm tra URL gửi đi

    $.ajax({
        url: url,
        method: "GET",
        success: function (response) {
            const transactions = response.content;
            totalPages = response.totalPages;

            $("#transactionTableBody").empty();
            transactions.forEach(transaction => {
                $("#transactionTableBody").append(`
                    <tr>
                        <td>${transaction.id}</td>
                        <td>${transaction.transactionId}</td>
                        <td>${transaction.transactionDate}</td>
                        <td>${transaction.serviceType}</td>
                        <td>${transaction.unitPrice.toLocaleString()}</td>
                        <td>${transaction.area}</td>
                        <td>${transaction.customer.customerName}</td>
                        <td>
                            <button onclick="viewDetail(${transaction.id})">Detail</button>
                            <button onclick="deleteTransaction(${transaction.id})">Delete</button>
                        </td>
                    </tr>
                `);
            });

            $("#pageInfo").text(`Page ${currentPage + 1} of ${totalPages}`);
            $("#prevPage").prop("disabled", currentPage === 0);
            $("#nextPage").prop("disabled", currentPage >= totalPages - 1);
        },
        error: function () {
            alert("Failed to load data!");
        }
    });
}

// Xóa giao dịch
function deleteTransaction(id) {
    if (confirm("Are you sure you want to delete this transaction?")) {
        $.ajax({
            url: `http://localhost:8080/api/transactions/${id}`,
            method: "DELETE",
            success: function () {
                alert("Transaction deleted successfully!");
                loadTransactions(); // Cập nhật danh sách sau khi xóa
            },
            error: function () {
                alert("Failed to delete transaction!");
            }
        });
    }
}

// Điều hướng phân trang
$(document).ready(function () {
    loadTransactions();

    $("#prevPage").click(function () {
        if (currentPage > 0) {
            currentPage--;
            loadTransactions();
        }
    });

    $("#nextPage").click(function () {
        if (currentPage < totalPages - 1) {
            currentPage++;
            loadTransactions();
        }
    });

    $("#pageSize").change(function () {
        pageSize = parseInt($(this).val());
        currentPage = 0;
        loadTransactions();
    });

    $("#searchSelect").change(function () {
        searchQuery = $(this).val().trim();
        currentPage = 0;
        loadTransactions();
    });
});

// Xem chi tiết giao dịch
function viewDetail(id) {
    window.location.href = `detail.html?id=${id}`;
}

// Sample data for charts
const monthlyRevenueData = {
    labels: ['Tháng 1', 'Tháng 2', 'Tháng 3', 'Tháng 4', 'Tháng 5', 'Tháng 6', 'Tháng 7', 'Tháng 8', 'Tháng 9', 'Tháng 10', 'Tháng 11', 'Tháng 12'],
    datasets: [{
        label: 'Doanh thu',
        data: [100, 200, 300, 400, 500, 600, 700, 800, 900, 1000, 1100, 1200],
        backgroundColor: 'rgba(54, 162, 235, 0.2)',
        borderColor: 'rgba(54, 162, 235, 1)',
        borderWidth: 1
    }]
};

const categoryRevenueData = {
    labels: ['Thể loại 1', 'Thể loại 2', 'Thể loại 3', 'Thể loại 4', 'Thể loại 5'],
    datasets: [{
        label: 'Doanh thu',
        data: [100, 200, 300, 400, 500],
        backgroundColor: 'rgba(255, 99, 132, 0.2)',
        borderColor: 'rgba(255, 99, 132, 1)',
        borderWidth: 1
    }]
};

const campaignRevenueData = {
    labels: ['Chiến dịch 1', 'Chiến dịch 2', 'Chiến dịch 3', 'Chiến dịch 4', 'Chiến dịch 5'],
    datasets: [{
        label: 'Doanh thu',
        data: [100, 200, 300, 400, 500],
        backgroundColor: 'rgba(75, 192, 192, 0.2)',
        borderColor: 'rgba(75, 192, 192, 1)',
        borderWidth: 1
    }]
};

window.onload = function() {
    initializeChart('categoryRevenueChart', 'pie', revenueByCategory.labels, revenueByCategory.data, 'Doanh thu', [
        'rgba(255, 99, 132, 0.2)',
        'rgba(54, 162, 235, 0.2)',
        'rgba(255, 206, 86, 0.2)',
        'rgba(75, 192, 192, 0.2)',
        'rgba(153, 102, 255, 0.2)',
        'rgba(201, 203, 207, 0.2)'
    ], [
        'rgba(255, 99, 132, 1)',
        'rgba(54, 162, 235, 1)',
        'rgba(255, 206, 86, 1)',
        'rgba(75, 192, 192, 1)',
        'rgba(153, 102, 255, 1)',
        'rgba(201, 203, 207, 1)'
    ]);

    initializeChart('campaignRevenueChart', 'bar', revenueByCampaign.labels, revenueByCampaign.data, 'Doanh thu', 'rgba(75, 192, 192, 0.2)', 'rgba(75, 192, 192, 1)');

    initializeChart('timeRangeRevenueChart', 'line', revenueByTimeRange.labels, revenueByTimeRange.data, 'Doanh thu', 'rgba(54, 162, 235, 0.2)', 'rgba(54, 162, 235, 1)');
};

function initializeChart(elementId, type, labels, data, label, backgroundColor, borderColor) {
    const ctx = document.getElementById(elementId);
    if (ctx) {
        new Chart(ctx.getContext('2d'), {
            type: type,
            data: {
                labels: labels,
                datasets: [{
                    label: label,
                    data: data,
                    backgroundColor: Array.isArray(backgroundColor) ? backgroundColor : [backgroundColor],
                    borderColor: Array.isArray(borderColor) ? borderColor : [borderColor],
                    borderWidth: 1
                }]
            },
            options: {
                responsive: true,
                scales: type === 'bar' || type === 'line' ? {
                    y: {
                        beginAtZero: true
                    }
                } : {},
                plugins: {
                    tooltip: {
                        callbacks: {
                            label: function(tooltipItem) {
                                return tooltipItem.label + ': ' + tooltipItem.raw.toLocaleString('vi-VN', { style: 'currency', currency: 'VND' });
                            }
                        }
                    }
                }
            }
        });
    }
}

function exportReport() {
    console.log('Exporting report as PDF...');
    const { jsPDF } = window.jspdf;

    // Use html2canvas to capture the dashboard content
    html2canvas(document.querySelector('main')).then(canvas => {
        const imgData = canvas.toDataURL('image/png');
        const doc = new jsPDF('p', 'mm', 'a4');
        const imgProps = doc.getImageProperties(imgData);
        const pdfWidth = doc.internal.pageSize.getWidth();
        const pdfHeight = (imgProps.height * pdfWidth) / imgProps.width;

        doc.addImage(imgData, 'PNG', 0, 0, pdfWidth, pdfHeight);
        doc.save('report.pdf');
    });
}

const timeRangeElement = document.getElementById('timeRange');
if (timeRangeElement) {
    timeRangeElement.addEventListener('change', function() {
        const selectedTimeRange = this.value;
        console.log('Selected time range:', selectedTimeRange);
    });
}

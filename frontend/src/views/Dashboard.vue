<template>
  <div class="dashboard">
    <h2>Dashboard</h2>
    <div class="summary-cards">
      <div class="card">
        <h3>Total Balance</h3>
        <p>$0.00</p>
      </div>
      <div class="card">
        <h3>Income</h3>
        <p>$0.00</p>
      </div>
      <div class="card">
        <h3>Expenses</h3>
        <p>$0.00</p>
      </div>
    </div>
    <div class="chart-container">
      <canvas id="expenseChart"></canvas>
    </div>
  </div>
</template>

<script>
import { Chart, registerables } from 'chart.js';
import { Bar } from 'vue-chartjs';

export default {
  name: 'Dashboard',
  extends: Bar,
  mounted() {
    Chart.register(...registerables);
    this.renderChart({
      labels: ['Jan', 'Feb', 'Mar', 'Apr', 'May', 'Jun'],
      datasets: [
        {
          label: 'Income',
          backgroundColor: '#4CAF50',
          data: [40, 20, 12, 39, 10, 40]
        },
        {
          label: 'Expenses',
          backgroundColor: '#F44336',
          data: [15, 25, 18, 32, 15, 25]
        }
      ]
    }, { responsive: true });
  }
}
</script>

<style scoped>
.dashboard {
  padding: 20px;
}
.summary-cards {
  display: flex;
  justify-content: space-between;
  margin-bottom: 20px;
}
.card {
  background: #fff;
  border-radius: 8px;
  padding: 20px;
  box-shadow: 0 2px 4px rgba(0,0,0,0.1);
  width: 30%;
}
.chart-container {
  background: #fff;
  border-radius: 8px;
  padding: 20px;
  box-shadow: 0 2px 4px rgba(0,0,0,0.1);
}
</style>
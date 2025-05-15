<template>
  <div class="reports">
    <h2>Reports</h2>
    <div class="report-filters">
      <label>From: <input type="date" v-model="fromDate"></label>
      <label>To: <input type="date" v-model="toDate"></label>
      <button @click="generateReport">Generate</button>
    </div>
    
    <div class="chart-container">
      <canvas id="categoryChart"></canvas>
    </div>
    
    <div class="report-summary">
      <h3>Summary</h3>
      <div class="summary-item">
        <span>Total Income:</span>
        <span class="income">$0.00</span>
      </div>
      <div class="summary-item">
        <span>Total Expenses:</span>
        <span class="expense">$0.00</span>
      </div>
      <div class="summary-item">
        <span>Net Balance:</span>
        <span>$0.00</span>
      </div>
    </div>
  </div>
</template>

<script>
import { Chart, registerables } from 'chart.js';
import { Pie } from 'vue-chartjs';

export default {
  name: 'Reports',
  extends: Pie,
  data() {
    return {
      fromDate: '',
      toDate: ''
    }
  },
  mounted() {
    Chart.register(...registerables);
    this.renderChart({
      labels: ['Food', 'Transport', 'Entertainment', 'Utilities', 'Other'],
      datasets: [
        {
          backgroundColor: ['#FF6384', '#36A2EB', '#FFCE56', '#4BC0C0', '#9966FF'],
          data: [300, 150, 180, 200, 100]
        }
      ]
    }, { responsive: true });
  },
  methods: {
    generateReport() {
      // TODO: Implement report generation logic
    }
  }
}
</script>

<style scoped>
.reports {
  padding: 20px;
}
.report-filters {
  display: flex;
  gap: 10px;
  margin-bottom: 20px;
}
.chart-container {
  max-width: 500px;
  margin: 0 auto 30px;
  background: #fff;
  border-radius: 8px;
  padding: 20px;
  box-shadow: 0 2px 4px rgba(0,0,0,0.1);
}
.report-summary {
  background: #fff;
  border-radius: 8px;
  padding: 20px;
  box-shadow: 0 2px 4px rgba(0,0,0,0.1);
}
.summary-item {
  display: flex;
  justify-content: space-between;
  margin-bottom: 10px;
}
.income {
  color: #4CAF50;
}
.expense {
  color: #F44336;
}
</style>
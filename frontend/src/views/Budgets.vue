<template>
  <div class="budgets">
    <h2>Budgets</h2>
    <div class="budget-list">
      <div class="budget-item" v-for="budget in budgets" :key="budget.category">
        <div class="budget-info">
          <h3>{{ budget.category }}</h3>
          <div class="progress-bar">
            <div class="progress" :style="{ width: budget.progress + '%' }"></div>
          </div>
          <p>{{ budget.spent }} / {{ budget.limit }}</p>
        </div>
        <button @click="editBudget(budget)">Edit</button>
      </div>
    </div>
    <button @click="showAddBudget = true">Add Budget</button>
    
    <div class="modal" v-if="showAddBudget || editingBudget">
      <div class="modal-content">
        <h3>{{ editingBudget ? 'Edit Budget' : 'Add New Budget' }}</h3>
        <form @submit.prevent="saveBudget">
          <label>Category: 
            <select v-model="currentBudget.category" required>
              <option v-for="category in categories" :key="category" :value="category">{{ category }}</option>
            </select>
          </label>
          <label>Limit: <input type="number" step="0.01" v-model="currentBudget.limit" required></label>
          <div class="modal-buttons">
            <button type="button" @click="cancelBudget">Cancel</button>
            <button type="submit">Save</button>
          </div>
        </form>
      </div>
    </div>
  </div>
</template>

<script>
import { API_BASE_URL } from '../config';

export default {
  name: 'Budgets',
  data() {
    return {
      showAddBudget: false,
      editingBudget: false,
      categories: ['Food', 'Transport', 'Entertainment', 'Utilities', 'Other'],
      budgets: [
        { category: 'Food', limit: 500, spent: 320, progress: 64 },
        { category: 'Transport', limit: 200, spent: 150, progress: 75 },
        { category: 'Entertainment', limit: 300, spent: 180, progress: 60 }
      ],
      currentBudget: {
        category: '',
        limit: 0
      }
    }
  },
  methods: {
    editBudget(budget) {
      this.editingBudget = true;
      this.currentBudget = { ...budget };
    },
    saveBudget() {
      if (this.editingBudget) {
        const index = this.budgets.findIndex(b => b.category === this.currentBudget.category);
        if (index !== -1) {
          this.budgets.splice(index, 1, { ...this.currentBudget, spent: 0, progress: 0 });
        }
      } else {
        this.budgets.push({ ...this.currentBudget, spent: 0, progress: 0 });
      }
      this.cancelBudget();
    },
    cancelBudget() {
      this.showAddBudget = false;
      this.editingBudget = false;
      this.currentBudget = {
        category: '',
        limit: 0
      };
    }
  }
}
</script>

<style scoped>
.budgets {
  padding: 20px;
}
.budget-list {
  margin-bottom: 20px;
}
.budget-item {
  display: flex;
  justify-content: space-between;
  align-items: center;
  background: #fff;
  border-radius: 8px;
  padding: 15px;
  margin-bottom: 10px;
  box-shadow: 0 2px 4px rgba(0,0,0,0.1);
}
.budget-info {
  flex-grow: 1;
}
.progress-bar {
  height: 10px;
  background: #e0e0e0;
  border-radius: 5px;
  margin: 10px 0;
}
.progress {
  height: 100%;
  background: #4CAF50;
  border-radius: 5px;
}
.modal {
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background-color: rgba(0,0,0,0.5);
  display: flex;
  justify-content: center;
  align-items: center;
}
.modal-content {
  background: white;
  padding: 20px;
  border-radius: 8px;
  width: 400px;
}
.modal-buttons {
  display: flex;
  justify-content: flex-end;
  gap: 10px;
  margin-top: 20px;
}
</style>
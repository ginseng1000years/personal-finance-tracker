<template>
  <div class="transactions">
  <div class="top-menu">
    <h2>Transactions</h2>
    <button @click="showAddTransaction = true" class="add-transaction">Add Transaction</button>
  </div>
  <div class="transaction-filters">
    <input type="text" placeholder="Search transactions" v-model="searchQuery">
    <div class="category-selector">
      <select v-model="selectedCategory">
        <option value="">All Categories</option>
        <option v-for="category in categories" :key="category" :value="category.name">{{ category.name }} ({{ category.type }})</option>
      </select>
    </div>
  </div>
    <table>
      <thead>
        <tr>
          <th>Date</th>
          <th>Description</th>
          <th>Category</th>
          <th>Amount</th>
        </tr>
      </thead>
      <tbody>
        <tr v-for="transaction in filteredTransactions" :key="transaction.id">
          <td>{{ transaction.date }}</td>
          <td>{{ transaction.description }}</td>
          <td>{{ transaction.category }}</td>
          <td :class="{ 'income': transaction.amount > 0, 'expense': transaction.amount < 0 }">
            {{ transaction.amount > 0 ? '+' : '' }}{{ transaction.amount }}
          </td>
        </tr>
      </tbody>
    </table>

    
    <div class="modal" v-if="showAddTransaction">
      <div class="modal-content">
        <h3>Add New Transaction</h3>
        
        <CategoryManager 
          :show="showCategoryManager" 
          :categories="categories"
          @update:show="showCategoryManager = $event"
          @update:categories="categories = $event"
        />
        <form @submit.prevent="addTransaction">
          <label>Date: <input type="date" v-model="newTransaction.date" required></label>
          <label>Description: <input type="text" v-model="newTransaction.description" required></label>
          <label>Category: 
            <select v-model="newTransaction.category" required>
              <option v-for="category in categories" :key="category.name" :value="category.name">{{ category.name }} ({{ category.type }})</option>
            </select>
          </label>
          <label>Category Type: 
            <select v-model="newTransaction.categoryType" required>
              <option value="expense">Expense</option>
              <option value="income">Income</option>
            </select>
          </label>
          <label>Amount: <input type="number" step="0.01" v-model="newTransaction.amount" required></label>
          <div class="modal-buttons">
            <button type="button" @click="showAddTransaction = false">Cancel</button>
            <button type="submit">Save</button>
          </div>
        </form>
      </div>
    </div>
  </div>
</template>

<script>
import axios from 'axios';
import { API_BASE_URL } from '../config';
import CategoryManager from './Category.vue';

export default {
  name: 'Transactions',
  data() {
    return {
      searchQuery: '',
      selectedCategory: '',
      showAddTransaction: false,
      showCategoryManager: false,
      categories: [],
      transactions: [],
      newTransaction: {
        date: '',
        description: '',
        category: '',
        categoryType: 'expense',
        amount: 0
      },
      newCategory: '',
      newCategoryType: 'expense',
      editCategory: {
        oldName: '',
        newName: ''
      }
    }
  },
  computed: {
    filteredTransactions() {
      return this.transactions.filter(t => {
        const matchesSearch = t.description && t.description.toLowerCase().includes(this.searchQuery.toLowerCase());
        const matchesCategory = !this.selectedCategory || t.category === this.selectedCategory;
        return matchesSearch && matchesCategory;
      });
    }
  },
  created() {
    this.fetchTransactions();
    this.fetchCategories();
  },
  methods: {
    async fetchCategories() {
      try {
        const response = await axios.get(`${API_BASE_URL}/categories`);
        this.categories = response.data.content;
      } catch (error) {
        console.error('Error fetching categories:', error);
        alert('Failed to load categories. Please try again later.');
      }
    },
    async addCategory() {
      if (!this.newCategory) return;
      try {
        await axios.post(`${API_BASE_URL}/categories`, { 
          name: this.newCategory,
          type: this.newCategoryType || 'expense'
        });
        await this.fetchCategories();
        this.newCategory = '';
        this.newCategoryType = 'expense';
      } catch (error) {
        console.error('Error adding category:', error);
        alert('Failed to add category. Please try again.');
      }
    },
    async updateCategory() {
      if (!this.editCategory.newName) return;
      try {
        await axios.put(`${API_BASE_URL}/categories`, {
          oldName: this.editCategory.oldName,
          newName: this.editCategory.newName
        });
        await this.fetchCategories();
        this.editCategory = { oldName: '', newName: '' };
      } catch (error) {
        console.error('Error updating category:', error);
        alert('Failed to update category. Please try again.');
      }
    },
    async deleteCategory(category) {
      if (!confirm(`Are you sure you want to delete category: ${category}?`)) return;
      try {
        await axios.delete(`${API_BASE_URL}/categories`, { data: { name: category } });
        await this.fetchCategories();
      } catch (error) {
        console.error('Error deleting category:', error);
        alert('Failed to delete category. Please try again.');
      }
    },
    async fetchTransactions() {
      try {
        const response = await axios.get(`${API_BASE_URL}/transactions`);
        this.transactions = response.data;
      } catch (error) {
        console.error('Error fetching transactions:', error);
        alert('Failed to load transactions. Please try again later.');
      }
    },
    async addTransaction() {
      try {
        const response = await axios.post(`${API_BASE_URL}/transactions`, this.newTransaction);
        this.transactions.push(response.data);
        this.showAddTransaction = false;
        this.resetNewTransaction();
      } catch (error) {
        console.error('Error adding transaction:', error);
        alert('Failed to add transaction. Please check your input and try again.');
      }
    },
    resetNewTransaction() {
      this.newTransaction = {
        date: '',
        description: '',
        category: '',
        amount: 0
      };
    }
  }
}
</script>



<style scoped>
.transactions {
  padding: 24px;
  background-color: #f8f9fa;
  border-radius: 8px;
}
.top-menu {
  display: flex;
  align-items: center;
  gap: 16px;
  margin-bottom: 16px;
}
.transaction-filters {
  display: flex;
  gap: 16px;
  margin-bottom: 24px;
}
.category-selector {
  display: flex;
  gap: 8px;
}
.categories-link {
  cursor: pointer;
  color: #1976d2;
  text-decoration: underline;
}
.categories-link:hover {
  color: #1565c0;
}
.category-management {
  display: flex;
  flex-direction: column;
  gap: 16px;
}
.category-management > div {
  padding: 16px;
  border: 1px solid #e0e0e0;
  border-radius: 4px;
}
.category-list ul {
  list-style: none;
  padding: 0;
}
.category-list li {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 8px 0;
}
.category-list button {
  margin-left: 8px;
  padding: 4px 8px;
  font-size: 12px;
}
table {
  width: 100%;
  border-collapse: collapse;
  margin-bottom: 24px;
  box-shadow: 0 1px 3px rgba(0,0,0,0.1);
}
th, td {
  padding: 16px;
  text-align: left;
  border-bottom: 1px solid #e0e0e0;
}
th {
  background-color: #1976d2;
  color: white;
  font-weight: 500;
}
.income {
  color: #2e7d32;
  font-weight: 500;
}
.expense {
  color: #c62828;
  font-weight: 500;
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
  z-index: 1000;
}

.modal-content {
  background: white;
  padding: 24px;
  border-radius: 8px;
  width: 500px;
  max-width: 90%;
  max-height: 80vh;
  overflow-y: auto;
  box-shadow: 0 4px 6px rgba(0,0,0,0.1);
}
.modal-content {
  background: white;
  padding: 24px;
  border-radius: 8px;
  width: 400px;
  box-shadow: 0 4px 6px rgba(0,0,0,0.1);
}
.modal-buttons {
  display: flex;
  justify-content: flex-end;
  gap: 16px;
  margin-top: 24px;
}
button {
  background-color: #1976d2;
  color: white;
  padding: 8px 16px;
  border: none;
  border-radius: 4px;
  cursor: pointer;
  transition: background-color 0.3s;
}
button:hover {
  background-color: #1565c0;
}
input, select {
  padding: 8px 12px;
  border: 1px solid #e0e0e0;
  border-radius: 4px;
  font-size: 14px;
}
</style>
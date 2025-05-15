<template>
  <div class="categories">
    <h2>Categories</h2>
    
    <div class="actions">
      <button @click="showAddModal = true">Add Category</button>
    </div>
    
    <div class="table-responsive">
      <table class="category-table">
        <thead>
          <tr>
            <th>Name</th>
            <th>Type</th>
            <th>Actions</th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="category in categories.content" :key="category.id">
            <td>{{ category.name }}</td>
            <td>{{ category.type }}</td>
            <td>
              <button @click="editCategory(category)">Edit</button>
              <button @click="confirmDelete(category)">Delete</button>
            </td>
          </tr>
        </tbody>
      </table>
    </div>
    
    <div class="pagination-controls">
      <button @click="prevPage" :disabled="categories.totalPages <= 1 || currentPage === 0">
        <i class="fas fa-chevron-left"></i> Previous
      </button>
      <span class="page-info">Page {{ currentPage + 1 }} of {{ categories.totalPages }}</span>
      <button @click="nextPage" :disabled="categories.totalPages <= 1 || !hasMore">
        Next <i class="fas fa-chevron-right"></i>
      </button>
    </div>
    
    <!-- Add/Edit Modal -->
    <div class="modal" v-if="showAddModal || showEditModal">
      <div class="modal-content">
        <h3>{{ showAddModal ? 'Add' : 'Edit' }} Category</h3>
        <form @submit.prevent="saveCategory">
          <div class="form-group">
            <label>Name</label>
            <input v-model="currentCategory.name" required>
          </div>
          <div class="form-group">
            <label>Type</label>
            <select v-model="currentCategory.type" required>
              <option value="income">Income</option>
              <option value="expense">Expense</option>
            </select>
          </div>
          <div class="modal-buttons">
            <button type="button" @click="closeModal">Cancel</button>
            <button type="submit">Save</button>
          </div>
        </form>
      </div>
    </div>
    
    <!-- Delete Confirmation Modal -->
    <div class="modal" v-if="showDeleteModal">
      <div class="modal-content">
        <h3>Confirm Delete</h3>
        <p>Are you sure you want to delete "{{ currentCategory.name }}"?</p>
        <div class="modal-buttons">
          <button @click="showDeleteModal = false">Cancel</button>
          <button @click="deleteCategory">Delete</button>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import axios from 'axios';
import { API_BASE_URL } from '../config';

export default {
  name: 'Categories',
  data() {
    return {
      categories: { content: [], totalPages: 0 },
      showAddModal: false,
      showEditModal: false,
      showDeleteModal: false,
      currentCategory: {
        id: null,
        name: '',
        type: 'expense'
      },
      currentPage: 0,
      pageSize: 10,
      hasMore: false
    }
  },
  methods: {
    async fetchCategories() {
      try {
        const response = await axios.get(`${API_BASE_URL}/categories`, {
          params: {
            page: this.currentPage,
            size: this.pageSize
          }
        });
        this.categories = response.data;
        this.hasMore = this.categories.totalPages > 1 && this.currentPage < this.categories.totalPages - 1;
      } catch (error) {
        console.error('Error fetching categories:', error);
        alert('Failed to load categories. Please try again later.');
      }
    },
    
    nextPage() {
      this.currentPage++;
      this.fetchCategories();
    },
    
    prevPage() {
      this.currentPage--;
      this.fetchCategories();
    },
    editCategory(category) {
      this.currentCategory = { ...category };
      this.showEditModal = true;
    },
    confirmDelete(category) {
      this.currentCategory = { ...category };
      this.showDeleteModal = true;
    },
    async saveCategory() {
      try {
        if (this.currentCategory.id) {
          await axios.put(`${API_BASE_URL}/categories/${this.currentCategory.id}`, this.currentCategory);
        } else {
          await axios.post(`${API_BASE_URL}/categories`, this.currentCategory);
        }
        await this.fetchCategories();
        this.closeModal();
      } catch (error) {
        console.error('Error saving category:', error);
        alert('Failed to save category. Please try again.');
      }
    },
    async deleteCategory() {
      try {
        await axios.delete(`${API_BASE_URL}/categories/${this.currentCategory.id}`);
        await this.fetchCategories();
        this.showDeleteModal = false;
      } catch (error) {
        console.error('Error deleting category:', error);
        alert('Failed to delete category. Please try again.');
      }
    },
    closeModal() {
      this.showAddModal = false;
      this.showEditModal = false;
      this.currentCategory = {
        id: null,
        name: '',
        type: 'expense'
      };
    }
  },
  mounted() {
    this.fetchCategories();
  }
}
</script>

<style scoped>
.categories {
  padding: 20px;
}

.actions {
  margin-bottom: 20px;
}

.table-responsive {
  overflow-x: auto;
}

.category-table {
  width: 100%;
  border-collapse: collapse;
}

.category-table th,
.category-table td {
  padding: 0.5rem;
  border: 1px solid #ddd;
  text-align: left;
}

@media (max-width: 768px) {
  .category-table {
    display: block;
  }
  
  .category-table thead {
    display: none;
  }
  
  .category-table tbody tr {
    display: block;
    margin-bottom: 1rem;
    border: 1px solid #ddd;
  }
  
  .category-table tbody td {
    display: block;
    text-align: right;
    padding-left: 50%;
    position: relative;
  }
  
  .category-table tbody td::before {
    content: attr(data-label);
    position: absolute;
    left: 0.5rem;
    width: 50%;
    padding-right: 0.5rem;
    text-align: left;
    font-weight: bold;
  }
}

.category-table th, .category-table td {
  padding: 12px;
  text-align: left;
  border-bottom: 1px solid #e0e0e0;
}

.category-table th:nth-child(3),
.category-table td:nth-child(3) {
  width: 180px;
}

.category-table th {
  background-color: #f5f5f5;
}

button {
  background-color: #1976d2;
  color: white;
  padding: 8px 16px;
  border: none;
  border-radius: 4px;
  cursor: pointer;
  margin-right: 8px;
  transition: background-color 0.3s;
}

button:hover {
  background-color: #1565c0;
}

button:disabled {
  background-color: #b0bec5;
  cursor: not-allowed;
  opacity: 0.7;
}

button:disabled:hover {
  background-color: #b0bec5;
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
  width: 400px;
  box-shadow: 0 4px 6px rgba(0,0,0,0.1);
}

.form-group {
  margin-bottom: 16px;
}

.form-group label {
  display: block;
  margin-bottom: 8px;
}

.form-group input, .form-group select {
  width: 100%;
  padding: 8px;
  border: 1px solid #e0e0e0;
  border-radius: 4px;
}

.modal-buttons {
  display: flex;
  justify-content: flex-end;
  gap: 16px;
  margin-top: 24px;
}
</style>
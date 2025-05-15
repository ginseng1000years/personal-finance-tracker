<template>
  <div class="modal" v-if="show">
    <div class="modal-content">
      <h3>Manage Categories</h3>
      <div class="category-management">
        <div class="add-category">
          <h4>Add New Category</h4>
          <input type="text" v-model="newCategory" placeholder="Category name">
          <button @click="addCategory">Add</button>
        </div>
        <div class="edit-category" v-if="editCategory.oldName">
          <h4>Edit Category</h4>
          <input type="text" v-model="editCategory.newName" placeholder="New name">
          <button @click="updateCategory">Update</button>
          <button @click="cancelEdit">Cancel</button>
        </div>
        <div class="category-list">
          <h4>Existing Categories</h4>
          <ul>
            <li v-for="category in categories" :key="category">
              {{ category }}
              <button @click="startEdit(category)">Edit</button>
              <button @click="deleteCategory(category)">Delete</button>
            </li>
          </ul>
        </div>
      </div>
      <div class="modal-buttons">
        <button @click="close">Close</button>
      </div>
    </div>
  </div>
</template>

<script>
import axios from 'axios';
import { API_BASE_URL } from '../config';

export default {
  name: 'CategoryManager',
  props: {
    show: Boolean,
    categories: Array
  },
  data() {
    return {
      newCategory: '',
      editCategory: {
        oldName: '',
        newName: ''
      }
    };
  },
  methods: {
    async fetchCategories() {
      try {
        const response = await axios.get(`${API_BASE_URL}/categories`);
        this.$emit('update:categories', response.data);
      } catch (error) {
        console.error('Error fetching categories:', error);
        alert('Failed to load categories. Please try again later.');
      }
    },
    async addCategory() {
      if (!this.newCategory) return;
      try {
        await axios.post(`${API_BASE_URL}/categories`, { name: this.newCategory });
        await this.fetchCategories();
        this.newCategory = '';
      } catch (error) {
        console.error('Error adding category:', error);
        alert('Failed to add category. Please try again.');
      }
    },
    startEdit(category) {
      this.editCategory = { oldName: category, newName: category };
    },
    cancelEdit() {
      this.editCategory = { oldName: '', newName: '' };
    },
    async updateCategory() {
      if (!this.editCategory.newName) return;
      try {
        await axios.put(`${API_BASE_URL}/categories`, {
          oldName: this.editCategory.oldName,
          newName: this.editCategory.newName
        });
        await this.fetchCategories();
        this.cancelEdit();
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
    close() {
      this.$emit('update:show', false);
    }
  }
};
</script>

<style scoped>
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
input {
  padding: 8px 12px;
  border: 1px solid #e0e0e0;
  border-radius: 4px;
  font-size: 14px;
}
</style>
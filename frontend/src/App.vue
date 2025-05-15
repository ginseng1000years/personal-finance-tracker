<template>
  <div id="app">
    <header>
      <div class="header-container">
        <h1>Money Manager</h1>
        <button class="mobile-menu-button" @click="toggleMenu">☰</button>
        <nav :class="{ 'mobile-menu': isMobileMenuOpen }">
          <ul>
            <li><router-link to="/">Dashboard</router-link></li>
            <li><router-link to="/transactions">Transactions</router-link></li>
            <li><router-link to="/budgets">Budgets</router-link></li>
            <li><router-link to="/reports">Reports</router-link></li>
            <li><router-link to="/categories">Categories</router-link></li>
          </ul>
        </nav>
      </div>
    </header>
    <main>
      <router-view/>
    </main>
  </div>
</template>

<script>
export default {
  name: 'App',
  data() {
    return {
      isMobileMenuOpen: false
    }
  },
  methods: {
    toggleMenu() {
      this.isMobileMenuOpen = !this.isMobileMenuOpen;
    }
  }
}
</script>

<style>
#app {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  color: #2c3e50;
}

header {
  background: #2c3e50;
  color: white;
  padding: 1rem 0;
}

.header-container {
  max-width: 1200px;
  margin: 0 auto;
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 0 2rem;
  position: relative;
}

.mobile-menu-button {
  display: none;
  background: none;
  border: none;
  color: white;
  font-size: 1.5rem;
  cursor: pointer;
}

nav ul {
  display: flex;
  gap: 1rem;
  list-style: none;
}

nav a {
  color: white;
  text-decoration: none;
}

@media (max-width: 768px) {
  .mobile-menu-button {
    display: block;
  }
  
  nav ul {
    flex-direction: column;
    position: absolute;
    top: 100%;
    left: 0;
    right: 0;
    background: #2c3e50;
    padding: 1rem;
    display: none;
  }
  
  .mobile-menu ul {
    display: flex;
  }
}

nav a {
  color: white;
  text-decoration: none;
  font-weight: 500;
  padding: 0.5rem 1rem;
  border-radius: 4px;
  transition: background-color 0.3s;
}

nav a:hover, nav a.router-link-exact-active {
  background: rgba(255, 255, 255, 0.1);
}

main {
  max-width: 1200px;
  margin: 2rem auto;
  padding: 0 2rem;
}
</style>
<template>
  <header>
    <div class="wrapper">
      <img src="@/assets/img//diccionario.ico" alt="Diccionario" class="logo" />
      <nav>
        <RouterLink to="/">Inicio</RouterLink>
        <RouterLink to="/idiomas">Idiomas</RouterLink>
        <RouterLink to="/palabras">Palabras</RouterLink>
      </nav>
    </div>
  </header>

  <RouterView />
</template>

<style scoped>
header {
  background-color: #333; 
  color: #fff; 
  line-height: 1.5;
  max-height: 100vh;
  position: fixed; 
  top: 0;
  left: 0;
  width: 100%; 
  z-index: 1000; 
  display: flex;
  align-items: center;
  padding: 0 1rem;
   
}

.wrapper {
  display: flex;
  align-items: center; 
  width: 100%;
}

.logo {
  width: 40px; 
  height: auto; 
  margin-right: 1rem; 
}

nav {
  display: flex;
  justify-content: center; 
  align-items: center;
  flex-grow: 1; 
  font-size: 14px;
  padding: 1rem;
}

nav a {
  color: #fff; 
  text-decoration: none; 
  padding: 0.5rem 1rem;
  border-radius: 5px; 
  margin: 0 0.5rem;
  transition: background-color 0.3s, color 0.3s;
}

nav a:hover {
  background-color: #555; 
  color: #fff; 
}

nav a.router-link-exact-active {
  background-color: #888888; 
}

@media (min-width: 1024px) {
  header {
    padding: 0 2rem; 
  }

  nav {
    font-size: 16px; 
    padding: 1rem 2rem; 
  }
}
</style>
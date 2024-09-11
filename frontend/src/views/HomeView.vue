<template>
  <div class="home-page">
    <div class="welcome-section">
      <h1>¡Bienvenido a nuestro diccionario!</h1>
      <p>Descubre y explora palabras e idiomas.</p>
      <div v-if="randomWord" class="random-word-container">
        <p class="random-word-title">Palabra del día:</p>
        <p><strong>Nombre:</strong> {{ randomWord.palabra }}</p>
        <p><strong>Descripción:</strong> {{ randomWord.descripcion }}</p>
        <p><strong>Ejemplo de uso:</strong> {{ randomWord.ejemploUso }}</p>
      </div>
      <div class="button-container">
        <button @click="goToPalabrasList" class="btn">Ver Lista de Palabras</button>
        <button @click="goToIdiomasList" class="btn">Ver Lista de Idiomas</button>
      </div>
    </div>
  </div>
</template>


<script setup>
import { ref, onMounted } from 'vue';
import axios from '@/axios'; // Asegúrate de que la ruta es correcta
import { useRouter } from 'vue-router';

const router = useRouter();
const randomWord = ref(null);

const fetchRandomWord = async () => {
  try {
    const response = await axios.get('/api/palabras/random'); // Asegúrate de que la URL es correcta
    const palabra = response.data;
    if (palabra) {
      randomWord.value = palabra; // Ajusta según el nombre de la propiedad en el objeto palabra
    } else {
      console.error('No se encontró ninguna palabra');
    }
  } catch (error) {
    console.error('Error al cargar palabra aleatoria:', error);
  }
};

const goToPalabrasList = () => {
  router.push({ name: 'PalabrasList' });
};

const goToIdiomasList = () => {
  router.push({ name: 'IdiomasList' });
};

onMounted(() => {
  fetchRandomWord();
});
</script>

<style scoped>
.home-page {
  background-image: url('@/assets/img/fondo.png'); /* Asegúrate de tener esta imagen en la carpeta correcta */
  background-size: cover;
  background-position: center;
  color: #fff;
  text-align: center;
  height: 100vh;
  display: flex;
  justify-content: center;
  align-items: center;
}

.welcome-section {
  background-color: rgba(0, 0, 0, 0.6); /* Fondo semi-transparente para mejorar la legibilidad */
  padding: 20px;
  border-radius: 10px;
  max-width: 500px;
}

.welcome-section h1 {
  font-size: 2.5rem;
  margin-bottom: 10px;
}

.welcome-section p {
  font-size: 1.2rem;
  margin: 10px 0;
}

.random-word-container {
  background-color: rgba(255, 255, 255, 0.1); /* Fondo semi-transparente para diferenciar la sección */
  padding: 10px;
  border-radius: 5px;
  margin-top: 20px;
}

.random-word-title {
  font-size: 1.5rem;
  margin-bottom: 10px;
}

.button-container {
  margin-top: 20px;
}

.btn {
  background-color: #007bff;
  border: none;
  color: white;
  padding: 10px 20px;
  font-size: 1rem;
  border-radius: 5px;
  cursor: pointer;
  margin: 5px;
  transition: background-color 0.3s;
}

.btn:hover {
  background-color: #0056b3;
}
</style>


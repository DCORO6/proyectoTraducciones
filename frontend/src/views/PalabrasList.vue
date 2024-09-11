<template>
  <div>
      <div class="header">
        <img src="@/assets/img/palabraslogo.png" alt="Palabras" class="header-image" />
        <select v-model="selectedIdioma" @change="filterByIdioma" class="language-select">
        <option value="">Todos los idiomas</option>
        <option v-for="idioma in idiomas" :key="idioma.id" :value="idioma.id">
          {{ idioma.nombre }}
        </option>
      </select>
  <button @click="goToCreateForm" class="btn-create">
    <img src="@/assets/img/aniadir.png" alt="Crear" title="Crear palabra" />
  </button>
  </div>
    <div v-if="loading" class="loading">Cargando...</div>
    <div v-else>
      <div v-if="palabras.length > 0" class="cards-container">
        <div v-for="palabra in palabras" :key="palabra.id" class="card">
          <div class="card-header">
            <strong>{{ palabra.palabra }}</strong>
          </div>
          <div class="card-body">
            <p class="card-description">{{ palabra.descripcion }}</p>
            <p><small>Dificultad: {{ palabra.nivelDificultad }}, Frecuencia: {{ palabra.frecuenciaUso }}</small></p>
          </div>
          <div class="card-footer">
            
            <button @click="editPalabra(palabra)" class="btn btn-edit">
              <img src="@/assets/img/editar.png" alt="Editar" title="Editar palabra"/>
            
            </button>
            <button @click="viewDetails(palabra)" class="btn btn-details">
              <img src="@/assets/img/ver.png" alt="Ver detalles" title="Ver detalles" />
            </button>
            <button @click="confirmDelete(palabra)" class="btn btn-delete">
              <img src="@/assets/img/eliminar.png" alt="Eliminar" title="Eliminar palabra"/>
            </button>
          </div>
        </div>
      </div>
      <p v-else style="margin-left: 10px;">No hay palabras disponibles.</p>
    </div>

    <!-- Modal de confirmación -->
    <div v-if="showModal" class="modal-overlay">
      <div class="modal">
        <h3>¿Estás seguro de que deseas eliminar esta palabra?</h3>
        <p><strong>{{ palabraAEliminar?.palabra }}</strong></p>
        <button @click="deletePalabra" class="btn-confirm">Confirmar</button>
        <button @click="cancelDelete" class="btn-cancel">Cancelar</button>
      </div>
    </div>

    <!-- Modal de detalles de la palabra -->
    <div v-if="showDetailsModal" class="modal-overlay">
      <div class="modal">
        <button @click="closeDetails" class="btn-close">×</button>
        <h2>Detalles</h2>
        <p><strong>Palabra:</strong> {{ selectedPalabra.palabra }}</p>
        <p><strong>Descripción:</strong> <span class="modal-description">{{ selectedPalabra.descripcion }}</span></p>
        <p class="example-box"><strong>Ejemplo de uso:</strong> <span class="modal-example">{{ selectedPalabra.ejemploUso }}</span></p>
        <p><strong>Nivel de dificultad:</strong> {{ selectedPalabra.nivelDificultad }}</p>
        <p><strong>Frecuencia de uso:</strong> {{ selectedPalabra.frecuenciaUso }}</p>
        <p><strong>Fecha de creación:</strong> {{ selectedPalabra.fechaCreacion }}</p>
        <p><strong>Idioma:</strong> {{ selectedPalabra.idioma.nombre }}</p>
      </div>
    </div>
  </div>
</template>


<script setup>
import { ref, onMounted } from 'vue';
import axios from '@/axios';
import { useToast } from 'vue-toast-notification';
import { useRouter } from 'vue-router';

const router = useRouter();
const toast = useToast();

const palabras = ref([]);
const idiomas = ref([]);
const loading = ref(true);
const showModal = ref(false);
const showDetailsModal = ref(false);
const palabraAEliminar = ref(null);
const selectedPalabra = ref(null);
const selectedIdioma = ref('');

const fetchPalabras = async () => {
  try {
    const response = await axios.get('/api/palabras');
    palabras.value = response.data;
  } catch (error) {
    console.error("Error al cargar palabras:", error);
  } finally {
    loading.value = false;
  }
};

const fetchIdiomas = async () => {
  try {
    const response = await axios.get('/api/idiomas');
    idiomas.value = response.data;
  } catch (error) {
    console.error("Error al cargar idiomas:", error);
  }
};

const filterByIdioma = () => {
  if (selectedIdioma.value) {
    palabras.value = palabras.value.filter(palabra => palabra.idioma.id === selectedIdioma.value);
  } else {
    fetchPalabras(); 
  }
};

const confirmDelete = (palabra) => {
  palabraAEliminar.value = palabra;
  showModal.value = true;
};

const cancelDelete = () => {
  palabraAEliminar.value = null;
  showModal.value = false;
};

const deletePalabra = async () => {
  try {
    await axios.delete(`/api/palabras/${palabraAEliminar.value.id}`);
    palabras.value = palabras.value.filter(p => p.id !== palabraAEliminar.value.id);
    toast.success("Palabra eliminada correctamente");
  } catch (error) {
    console.error("Error al eliminar palabra:", error);
    toast.error("Error al eliminar la palabra");
  } finally {
    cancelDelete();
  }
};

const goToCreateForm = () => {
  router.push({ name: 'PalabrasForm' }); 
};

const editPalabra = (palabra) => {
  router.push({ name: 'PalabrasForm', params: { id: palabra.id } });
};

const viewDetails = (palabra) => {
  selectedPalabra.value = palabra;
  showDetailsModal.value = true;
};

const closeDetails = () => {
  selectedPalabra.value = null;
  showDetailsModal.value = false;
};

onMounted(() => {
  fetchPalabras();
  fetchIdiomas(); // Cargar la lista de idiomas
});
</script>


<style scoped>
.cards-container {
  display: flex;
  flex-wrap: wrap;
  gap: 15px;
  justify-content: center;
  margin: 20px;
}

.card {
  background-color: #f5f4f4;
  border: 1px solid #ddd;
  border-radius: 8px;
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
  padding: 10px;
  width: 300px; /* Hacer la tarjeta más alargada */
  height: 100%; /* Hacer la tarjeta más alta */
  transition: transform 0.3s ease, box-shadow 0.3s ease;
}

.card:hover {
  transform: scale(1.02);
  box-shadow: 0 6px 12px rgba(0, 0, 0, 0.15);
}

.card-header {
  font-size: 1.2rem;
  font-weight: bold;
  margin-bottom: 8px;
  color: #333;
}

.card-body {
  font-size: 0.85rem; /* Reducir el tamaño del texto en la tarjeta */
  color: #555;
}

.card-description {
  overflow: hidden;
  text-overflow: ellipsis;
  display: -webkit-box;
  -webkit-line-clamp: 4;
  -webkit-box-orient: vertical;
}
.card-footer {
  margin-top: 12px;
  display: flex;
  gap: 10px; /* Espacio entre los botones */
}

.btn {
  display: flex;
  align-items: center;
  border: none;
  padding: 6px 10px;
  font-size: 0.75rem;
  color: white;
  cursor: pointer;
  border-radius: 4px;
  transition: background-color 0.3s ease;
  width: 40px;
}

.btn img {
  width: 20px; /* Ajusta el tamaño de las imágenes */
  height: 20px;
  margin-right: 6px; /* Espacio entre la imagen y el texto */
}

.btn-delete {
  background-color: #db8b8a;
}

.btn-delete:hover {
  background-color: #e97575;
}

.btn-edit {
  background-color: #ff9800;
}

.btn-edit:hover {
  background-color: #fb8c00;
}

.btn-details {
  background-color: #2196f3;
}

.btn-details:hover {
  background-color: #1976d2;
}
.btn-create {
  display: flex;
  align-items: center;
  justify-content: center; /* Centra el contenido horizontalmente */
  border: none;
  color: white;
  cursor: pointer;
  border-radius: 5px;
  font-size: 1rem;
  font-weight: bold;
  transition: background-color 0.3s ease;
  width: 80px; /* Aumentar el ancho para acomodar la imagen y el texto */
  height: 40px; /* Asegura que el botón tenga suficiente altura */
  background-color: #28a745; 
  text-align: center;
}

.btn-create img {
  width: 34px; 
  height: 35px;
}


.btn-create:hover {
  background-color: #218838; /* Color de fondo cuando el cursor está sobre el botón */
}


.modal-overlay {
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background: rgba(0, 0, 0, 0.6);
  display: flex;
  justify-content: center;
  align-items: center;
}

.modal {
  background-color: #fff;
  padding: 20px;
  border-radius: 12px;
  box-shadow: 0 8px 16px rgba(0, 0, 0, 0.2);
  width: 80%;
  max-width: 600px;
  position: relative;
  max-height: 80vh;
  overflow-y: auto;
}

.btn-close {
  position: absolute;
  top: 10px;
  right: 10px;
  background: none;
  border: none;
  font-size: 1.5rem;
  color: #333;
  cursor: pointer;
}

.btn-close:hover {
  color: #000;
}

.modal-description, .modal-example {
  overflow: hidden;
  text-overflow: ellipsis;
  display: -webkit-box;
  -webkit-line-clamp: 5;
  -webkit-box-orient: vertical;
}

.example-box {
  border: 1px solid #ddd;
  border-radius: 5px;
  padding: 10px;
  background-color: #f1f1f1;
  margin-top: 10px;
}

.btn-confirm {
  background-color: #4caf50;
  color: white;
  border: none;
  padding: 8px 16px;
  cursor: pointer;
  border-radius: 4px;
  transition: background-color 0.3s ease;
}

.btn-cancel {
  background-color: #f44336;
  color: white;
  border: none;
  padding: 8px 16px;
  cursor: pointer;
  border-radius: 4px;
  transition: background-color 0.3s ease;
  margin-left: 10px;
}

.btn-confirm:hover {
  background-color: #45a049;
}

.btn-cancel:hover {
  background-color: #e53935;
}

.btn-close-modal {
  background-color: #007bff;
  color: white;
  border: none;
  padding: 8px 16px;
  cursor: pointer;
  border-radius: 4px;
  transition: background-color 0.3s ease;
  margin-top: 10px;
}

.btn-close-modal:hover {
  background-color: #0056b3;
}

.loading {
  text-align: center;
  font-size: 1.5rem;
  color: #666;
}

.header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin: 20px;
  margin-top: 100px;
}

.header-image {
  width: 40px; 
  height: 40px; 
}
</style>

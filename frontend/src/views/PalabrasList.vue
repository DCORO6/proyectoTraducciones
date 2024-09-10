<template>
  <div>
    <div class="header">
      <button @click="goToCreateForm" class="btn-create">Crear Nueva Palabra</button>
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
            <button @click="confirmDelete(palabra)" class="btn-delete">Eliminar</button>
            <button @click="editPalabra(palabra)" class="btn-edit">Editar</button> 
            <button @click="viewDetails(palabra)" class="btn-details">Ver Detalles</button>
          </div>
        </div>
      </div>
      <p v-else>No hay palabras disponibles.</p>
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
const loading = ref(true);
const showModal = ref(false);
const showDetailsModal = ref(false);
const palabraAEliminar = ref(null);
const selectedPalabra = ref(null);

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
  router.push({ name: 'PalabrasForm', query: { id: palabra.id } });
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
});
</script>

<style scoped>
.cards-container {
  display: flex;
  flex-wrap: wrap;
  gap: 20px;
  justify-content: center;
  margin: 20px;
}

.card {
  background-color: #ffffff;
  border: 1px solid #e0e0e0;
  border-radius: 8px;
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
  padding: 20px;
  width: 300px;
  transition: transform 0.2s ease-in-out;
  overflow: hidden; /* Evita que el contenido sobresalga */
}

.card:hover {
  transform: scale(1.02);
}

.card-header {
  font-size: 1.5rem;
  font-weight: bold;
  margin-bottom: 10px;
}

.card-body {
  font-size: 1rem;
}

.card-description {
  overflow: hidden; /* Evita que el texto se desborde */
  text-overflow: ellipsis; /* Añade '...' si el texto es demasiado largo */
  display: -webkit-box;
  -webkit-line-clamp: 3; /* Número de líneas a mostrar */
  -webkit-box-orient: vertical;
}

.card-footer {
  margin-top: 10px;
  display: flex;
  justify-content: space-between;
}

.btn-delete {
  background-color: #ff4c4c;
  border: none;
  padding: 10px 20px;
  color: white;
  cursor: pointer;
  border-radius: 4px;
  transition: background-color 0.2s ease;
}

.btn-delete:hover {
  background-color: #e03e3e;
}

.btn-details {
  background-color: #007bff;
  border: none;
  padding: 10px 20px;
  color: white;
  cursor: pointer;
  border-radius: 4px;
  transition: background-color 0.2s ease;
}

.btn-details:hover {
  background-color: #0056b3;
}

.modal-overlay {
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background: rgba(0, 0, 0, 0.5);
  display: flex;
  justify-content: center;
  align-items: center;
}

.modal {
  background-color: #ffffff;
  padding: 20px;
  border-radius: 8px;
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.2);
  width: 400px;
  text-align: left;
  position: relative;
  max-height: 80vh; /* Limita la altura del modal */
  overflow-y: auto; /* Añade scroll si el contenido es demasiado largo */
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

.modal-description,
.modal-example {
  overflow: hidden;
  text-overflow: ellipsis;
  display: -webkit-box;
  -webkit-line-clamp: 5; /* Ajusta el número de líneas visibles */
  -webkit-box-orient: vertical;
}

.example-box {
  border: 1px solid #e0e0e0;
  border-radius: 4px;
  padding: 10px;
  background-color: #f9f9f9;
  margin-top: 10px;
  margin-bottom: 10px;
}

.btn-confirm {
  background-color: #4CAF50;
  color: white;
  border: none;
  padding: 10px 20px;
  margin-right: 10px;
  cursor: pointer;
  border-radius: 4px;
  transition: background-color 0.2s ease;
}

.btn-cancel {
  background-color: #f44336;
  color: white;
  border: none;
  padding: 10px 20px;
  cursor: pointer;
  border-radius: 4px;
  transition: background-color 0.2s ease;
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
  padding: 10px 20px;
  cursor: pointer;
  border-radius: 4px;
  transition: background-color 0.2s ease;
  margin-top: 10px;
}

.btn-close-modal:hover {
  background-color: #0056b3;
}

.loading {
  text-align: center;
  font-size: 1.5rem;
  color: #555;
}

.header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin: 20px;
}

.btn-create {
  background-color: #28a745;
  border: none;
  padding: 10px 20px;
  color: white;
  cursor: pointer;
  border-radius: 4px;
  transition: background-color 0.2s ease;
}

.btn-create:hover {
  background-color: #218838;
}

</style>

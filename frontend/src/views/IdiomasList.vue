<template>
    <div>
      <div class="header">
        <button @click="goToCreateForm" class="btn-create">Crear Nuevo Idioma</button>
      </div>
      <div v-if="loading" class="loading">Cargando...</div>
      <div v-else>
        <div v-if="idiomas.length > 0" class="cards-container">
          <div v-for="idioma in idiomas" :key="idioma.id" class="card">
            <div class="card-header">
              <strong>{{ idioma.nombre }}</strong>
            </div>
            <div class="card-body">
              <p><small>Código ISO: {{ idioma.codigoIso }}, Alfabeto: {{ formatAlfabeto(idioma.alfabeto) }}, Región: {{ idioma.region }}</small></p>
            </div>
            <div class="card-footer">
              <button @click="confirmDelete(idioma)" class="btn-delete">Eliminar</button>
              <button @click="editIdioma(idioma)" class="btn-edit">Editar</button>
              <button @click="viewDetails(idioma)" class="btn-details">Ver Detalles</button>
            </div>
          </div>
        </div>
        <p v-else>No hay idiomas disponibles.</p>
      </div>
  
      <!-- Modal de confirmación -->
      <div v-if="showModal" class="modal-overlay">
        <div class="modal">
          <h3>¿Estás seguro de que deseas eliminar este idioma?</h3>
          <p><strong>{{ idiomaAEliminar?.nombre }}</strong></p>
          <button @click="deleteIdioma" class="btn-confirm">Confirmar</button>
          <button @click="cancelDelete" class="btn-cancel">Cancelar</button>
        </div>
      </div>
  
      <!-- Modal de detalles del idioma -->
      <div v-if="showDetailsModal" class="modal-overlay">
        <div class="modal">
          <button @click="closeDetails" class="btn-close">×</button>
          <h2>Detalles</h2>
          <p><strong>Nombre:</strong> {{ selectedIdioma.nombre }}</p>
          <p><strong>Código ISO:</strong> {{ selectedIdioma.codigoIso }}</p>
          <p><strong>Alfabeto:</strong> {{ formatAlfabeto(selectedIdioma.alfabeto) }}</p>
          <p><strong>Región:</strong> {{ selectedIdioma.region }}</p>
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
  
  const idiomas = ref([]);
  const loading = ref(true);
  const showModal = ref(false);
  const showDetailsModal = ref(false);
  const idiomaAEliminar = ref(null);
  const selectedIdioma = ref(null);
  
  const fetchIdiomas = async () => {
    try {
      const response = await axios.get('/api/idiomas');
      idiomas.value = response.data;
    } catch (error) {
      console.error("Error al cargar idiomas:", error);
    } finally {
      loading.value = false;
    }
  };
  
  const formatAlfabeto = (alfabeto) => {
    if (!alfabeto) return '';
    const lowerCase = alfabeto.toLowerCase();
    return lowerCase.charAt(0).toUpperCase() + lowerCase.slice(1);
  };
  
  const confirmDelete = (idioma) => {
    idiomaAEliminar.value = idioma;
    showModal.value = true;
  };
  
  const cancelDelete = () => {
    idiomaAEliminar.value = null;
    showModal.value = false;
  };
  
  const deleteIdioma = async () => {
    try {
      await axios.delete(`/api/idiomas/${idiomaAEliminar.value.id}`);
      idiomas.value = idiomas.value.filter(i => i.id !== idiomaAEliminar.value.id);
      toast.success("Idioma eliminado correctamente");
    } catch (error) {
      console.error("Error al eliminar idioma:", error);
      toast.error("Error al eliminar el idioma");
    } finally {
      cancelDelete();
    }
  };
  
  const goToCreateForm = () => {
    router.push({ name: 'IdiomasForm' });
  };
  
  const editIdioma = (idioma) => {
    router.push({ name: 'IdiomasForm', query: { id: idioma.id } });
  };
  
  const viewDetails = (idioma) => {
    selectedIdioma.value = idioma;
    showDetailsModal.value = true;
  };
  
  const closeDetails = () => {
    selectedIdioma.value = null;
    showDetailsModal.value = false;
  };
  
  onMounted(() => {
    fetchIdiomas();
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
  
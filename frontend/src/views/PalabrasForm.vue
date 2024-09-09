<template>
  <form @submit.prevent="handleSubmit" class="add-palabra-form">
    <div class="form-group">
      <label for="palabra">Palabra:</label>
      <input
        type="text"
        id="palabra"
        v-model="formData.palabra"
        required
        class="form-control"
      />
    </div>
    <div class="form-group">
      <label for="descripcion">Descripción:</label>
      <textarea
        id="descripcion"
        v-model="formData.descripcion"
        class="form-control"
        required
      ></textarea>
    </div>
    <div class="form-group">
      <label for="ejemploUso">Ejemplo de uso:</label>
      <textarea
        id="ejemploUso"
        v-model="formData.ejemploUso"
        class="form-control"
        required
      ></textarea>
    </div>
    <div class="form-group">
      <label for="nivelDificultad">Nivel de dificultad (0-10):</label>
      <input
        type="number"
        id="nivelDificultad"
        v-model.number="formData.nivelDificultad"
        min="0"
        max="10"
        required
        class="form-control numeric-right"
      />
    </div>
    <div class="form-group">
      <label for="frecuenciaUso">Frecuencia de uso (0-10):</label>
      <input
        type="number"
        id="frecuenciaUso"
        v-model.number="formData.frecuenciaUso"
        min="0"
        max="10"
        required
        class="form-control numeric-right"
      />
    </div>
    <div class="form-group">
      <label for="fechaCreacion">Fecha de creación:</label>
      <input
        type="text"
        id="fechaCreacion"
        :value="formattedFechaCreacion"
        readonly
        class="form-control"
        disabled
        required
      />
    </div>
    <div class="form-group">
      <label for="idioma">Idioma:</label>
      <select
        id="idioma"
        v-model="formData.idiomaId"
        required
        class="form-control"
      >
        <option value="">Selecciona un idioma</option>
        <option v-for="idioma in idiomas" :key="idioma.id" :value="idioma.id">
          {{ idioma.nombre }}
        </option>
      </select>
    </div>
    <div class="form-buttons">
      <button type="submit" class="btn-submit">Agregar Palabra</button>
      <button type="button" class="btn-back" @click="goBack">Volver</button>
    </div>
  </form>
</template>

<script setup>
import { ref, onMounted, computed } from 'vue';
import axios from 'axios';
import { useRouter } from 'vue-router';

// Función para formatear la fecha
const formatDate = (date) => {
  const day = String(date.getDate()).padStart(2, '0');
  const month = String(date.getMonth() + 1).padStart(2, '0');
  const year = date.getFullYear();
  return `${day}-${month}-${year}`;
};

// Estado reactivo para los datos del formulario
const formData = ref({
  palabra: '',
  descripcion: '',
  ejemploUso: '',
  nivelDificultad: 0,
  frecuenciaUso: 0,
  fechaCreacion: formatDate(new Date()), // Fecha actual en formato DD-MM-YYYY
  idiomaId: ''
});

const idiomas = ref([]);

// Instancia del enrutador
const router = useRouter();

// Función para manejar el envío del formulario
const handleSubmit = () => {
  const { idiomaId, ...palabraData } = formData.value;

  axios.post(`/api/palabras/${idiomaId}/agregar`, palabraData)
    .then(response => {
      console.log('Palabra creada:', response.data);
      resetForm();
    })
    .catch(error => console.error('Error al crear palabra:', error));
};

// Función para restablecer el formulario
const resetForm = () => {
  formData.value = {
    palabra: '',
    descripcion: '',
    ejemploUso: '',
    nivelDificultad: 0,
    frecuenciaUso: 0,
    fechaCreacion: formatDate(new Date()), // Restablecer la fecha actual en formato DD-MM-YYYY
    idiomaId: ''
  };
};

// Función para retroceder en el historial
const goBack = () => {
  router.back(); 
};

// Computed para la fecha formateada
const formattedFechaCreacion = computed(() => formData.value.fechaCreacion);

// Cargar idiomas cuando el componente se monta
onMounted(() => {
  axios.get('/api/idiomas')
    .then(response => {
      idiomas.value = response.data;
    })
    .catch(error => console.error('Error al cargar idiomas:', error));
});
</script>

<style scoped>
.add-palabra-form {
  max-width: 600px;
  margin: auto;
  padding: 20px;
  border: 1px solid #ddd;
  border-radius: 8px;
  background-color: #f9f9f9;
}

.form-group {
  margin-bottom: 15px;
}

.form-group label {
  display: block;
  font-weight: bold;
  margin-bottom: 5px;
}

.form-group .form-control {
  width: 100%;
  padding: 8px;
  border: 1px solid #ccc;
  border-radius: 4px;
}

.form-group .form-control[readonly] {
  background-color: #e9ecef;
}

.btn-submit {
  background-color: #007bff;
  color: #fff;
  padding: 10px 15px;
  border: none;
  border-radius: 4px;
  cursor: pointer;
}

.btn-submit:hover {
  background-color: #0056b3;
}

.numeric-right {
  text-align: right;
}

.btn-back {
  background-color: #6c757d;
  color: #fff;
  padding: 10px 15px;
  border: none;
  border-radius: 4px;
  cursor: pointer;
}

.btn-back:hover {
  background-color: #5a6268;
}

.form-buttons {
  display: flex;
  justify-content: space-between;
}
</style>

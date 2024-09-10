<template>
  <form @submit.prevent="handleSubmit" class="add-palabra-form">
    <div class="form-group">
      <label for="palabra">Palabra:</label>
      <input type="text" id="palabra" v-model="formData.palabra" maxlength="30" required class="form-control" />
    </div>
    <div class="form-group">
      <label for="descripcion">Descripción:</label>
      <textarea id="descripcion" v-model="formData.descripcion" maxlength="500" class="form-control" required></textarea>
    </div>
    <div class="form-group">
      <label for="ejemploUso">Ejemplo de uso:</label>
      <textarea id="ejemploUso" v-model="formData.ejemploUso" maxlength="500" class="form-control" required></textarea>
    </div>
    <div class="form-group">
      <label for="nivelDificultad">Nivel de dificultad (0-10):</label>
      <input type="number" id="nivelDificultad" v-model.number="formData.nivelDificultad" min="0" max="10" required
        class="form-control numeric-right small-input" />
    </div>
    <div class="form-group">
      <label for="frecuenciaUso">Frecuencia de uso (0-10):</label>
      <input type="number" id="frecuenciaUso" v-model.number="formData.frecuenciaUso" min="0" max="10" required
        class="form-control numeric-right small-input" />
    </div>
    <div class="form-group">
      <label for="fechaCreacion">Fecha de creación:</label>
      <input type="text" id="fechaCreacion" :value="formattedFechaCreacion" readonly class="form-control small-input" disabled
        required />
    </div>
    <div class="form-group">
      <label for="idioma">Idioma:</label>
      <select
        id="idioma"
        v-model="formData.idiomaId"
        required
        class="form-control"
        @change="onIdiomaChange"
      >
        <option value="">Selecciona un idioma</option>
        <option v-for="idioma in idiomas" :key="idioma.id" :value="idioma.id">
          {{ idioma.nombre }}
        </option>
      </select>
    </div>
    <div class="form-buttons">
      <button type="submit" class="btn-submit">
        {{ isEditMode ? 'Actualizar Palabra' : 'Agregar Palabra' }}
      </button>
      <button type="button" class="btn-back" @click="goBack">Volver</button>
    </div>
  </form>
</template>

<script setup>
import { ref, onMounted, computed } from 'vue';
import axios from 'axios';
import { useRouter, useRoute } from 'vue-router';

// Función para formatear la fecha
const formatDate = (date) => {
  const day = String(date.getDate()).padStart(2, '0');
  const month = String(date.getMonth() + 1).padStart(2, '0');
  const year = date.getFullYear();
  return `${day}-${month}-${year}`;
};

const router = useRouter();
const route = useRoute();  // Usamos el route para obtener el ID si es edición

const isEditMode = ref(false);  // Estado para verificar si es modo edición

// Estado reactivo para los datos del formulario
const formData = ref({
  palabra: '',
  descripcion: '',
  ejemploUso: '',
  nivelDificultad: 0,
  frecuenciaUso: 0,
  fechaCreacion: formatDate(new Date()), // Fecha actual
  idiomaId: ''
});

const idiomas = ref([]);

const loadPalabraDetails = async (id) => {
  try {
    const response = await axios.get(`/api/palabras/${id}`);
    const palabra = response.data;
    console.log('Detalles de palabra cargada:', palabra); // Depuración adicional
    formData.value = {
      palabra: palabra.palabra,
      descripcion: palabra.descripcion,
      ejemploUso: palabra.ejemploUso,
      nivelDificultad: palabra.nivelDificultad,
      frecuenciaUso: palabra.frecuenciaUso,
      fechaCreacion: formatDate(new Date(palabra.fechaCreacion)),
      idiomaId: palabra.idioma.id
    };
  } catch (error) {
    console.error('Error al cargar la palabra:', error);
  }
};

const onIdiomaChange = () => {
  console.log('Idioma seleccionado:', formData.value.idiomaId);
};

const handleSubmit = () => {
  console.log('Form Data antes de enviar:', formData.value);
  
  const palabraData = { ...formData.value };
  const id = route.query.id || route.params.id;

  if (isEditMode.value) {
    console.log('Actualizando palabra con datos:', palabraData);
    axios.put(`/api/palabras/${id}`, palabraData)
      .then(() => {
        console.log('Palabra actualizada');
        router.push('/palabras');
      })
      .catch(error => console.error('Error al actualizar palabra:', error));
  } else {
    console.log('Creando palabra con datos:', palabraData);
    axios.post(`/api/palabras/${formData.value.idiomaId}/agregar`, palabraData)
      .then(() => {
        console.log('Palabra creada');
        router.push('/palabras');
        resetForm();
      })
      .catch(error => console.error('Error al crear palabra:', error));
  }
};

// Función para restablecer el formulario
const resetForm = () => {
  formData.value = {
    palabra: '',
    descripcion: '',
    ejemploUso: '',
    nivelDificultad: 0,
    frecuenciaUso: 0,
    fechaCreacion: formatDate(new Date()), // Restablecer la fecha actual
    idiomaId: ''
  };
};

// Función para volver atrás
const goBack = () => {
  router.back();
};

const formattedFechaCreacion = computed(() => formData.value.fechaCreacion);

onMounted(() => {
  axios.get('/api/idiomas')
    .then(response => {
      idiomas.value = response.data;
    })
    .catch(error => console.error('Error al cargar idiomas:', error));

  const id = route.query.id || route.params.id; // Ajusta según la configuración de la ruta
  if (id) {
    isEditMode.value = true;
    loadPalabraDetails(id);  // Cargar los datos de la palabra a editar
  }
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
  box-sizing: border-box; /* Asegura que el padding no afecte al tamaño del elemento */
}

.form-group textarea {
  height: 100px; /* Ajusta la altura del textarea según sea necesario */
  resize: vertical; /* Permite el ajuste vertical del textarea */
}

.small-input {
  font-size: 14px; /* Tamaño de fuente más pequeño */
  padding: 6px; /* Padding reducido */
}

.form-group .form-control[readonly] {
  background-color: #e9ecef;
}

.numeric-right {
  text-align: right;
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

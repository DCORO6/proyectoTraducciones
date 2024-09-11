<template>
  <form @submit.prevent="handleSubmit" class="add-palabra-form">
    <div class="form-group">
      <label for="palabra">Palabra:</label>
      <input type="text" id="palabra" v-model="formData.palabra" maxlength="30" required class="form-control" />
    </div>
    <div class="form-group">
      <label for="descripcion">Descripción:</label>
      <textarea id="descripcion" v-model="formData.descripcion" maxlength="500" class="form-control"
        required></textarea>
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
      <input type="text" id="fechaCreacion" :value="formattedFechaCreacion" readonly class="form-control small-input"
        disabled required />
    </div>
    <div class="form-group">
      <label for="idioma">Idioma:</label>
      <select id="idioma" v-model.number="formData.idiomaId" required class="form-control">
        <option value="">Selecciona un idioma</option>
        <option v-for="idioma in idiomas" :key="idioma.id" :value="idioma.id">
          {{ idioma.nombre }}
        </option>
      </select>


    </div>
    <div class="form-buttons">
      <button type="submit" class="btn-submit">
        {{ isEditMode ? 'Actualizar' : 'Agregar' }}
      </button>
      <button type="button" class="btn-back" @click="goBack">Volver</button>
    </div>
  </form>
</template>

<script setup>
import {ref, onMounted, computed } from 'vue';
import axios from 'axios';
import { useRouter, useRoute } from 'vue-router';
import { useToast } from 'vue-toast-notification';

// Crear instancia del toast
const toast = useToast();

// Función para formatear la fecha
const formatDate = (date) => {
  const day = String(date.getDate()).padStart(2, '0');
  const month = String(date.getMonth() + 1).padStart(2, '0');
  const year = date.getFullYear();
  return `${day}-${month}-${year}`;
};

const router = useRouter();
const route = useRoute();

const isEditMode = ref(false);

const formData = ref({
  palabra: '',
  descripcion: '',
  ejemploUso: '',
  nivelDificultad: 0,
  frecuenciaUso: 0,
  fechaCreacion: formatDate(new Date()),
  idiomaId: ''
});

const idiomas = ref([]);

// Cargar detalles de palabra (para modo edición)
const loadPalabraDetails = async (id) => {
  try {
    const response = await axios.get(`/api/palabras/${id}`);
    const palabra = response.data;
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
    toast.error('Error al cargar los detalles de la palabra.');
  }
};

const handleSubmit = () => {

  console.log('Datos del formulario antes de enviar:', formData.value); // Verifica aquí



  if (!formData.value.idiomaId) {
    toast.error('Por favor selecciona un idioma.');
    return;
  }

  if (formData.value.nivelDificultad < 0 || formData.value.nivelDificultad > 10) {
    toast.error('El nivel de dificultad debe estar entre 0 y 10.');
    return;
  }

  if (formData.value.frecuenciaUso < 0 || formData.value.frecuenciaUso > 10) {
    toast.error('La frecuencia de uso debe estar entre 0 y 10.');
    return;
  }

  const palabraData = { ...formData.value };
  palabraData.idioma = { id: palabraData.idiomaId }; // Asegúrate de que el formato es correcto para el backend

  const id = route.query.id || route.params.id;

  if (isEditMode.value) {
    axios.put(`/api/palabras/${id}`, palabraData)
      .then(() => {
        toast.success('Palabra actualizada correctamente.');
        router.push('/palabras');
      })
      .catch(error => {
        console.error('Error al actualizar palabra:', error);
        toast.error('Error al actualizar la palabra.');
      });
  } else {
    axios.post(`/api/palabras/${formData.value.idiomaId}/agregar`, palabraData)
      .then(() => {
        toast.success('Palabra añadida correctamente.');
        router.push('/palabras');
        resetForm();
      })
      .catch(error => {
        console.error('Error al crear palabra:', error);
        toast.error('Error al añadir la palabra.');
      });
  }
};

const resetForm = () => {
  formData.value = {
    palabra: '',
    descripcion: '',
    ejemploUso: '',
    nivelDificultad: 0,
    frecuenciaUso: 0,
    fechaCreacion: formatDate(new Date()),
    idiomaId: ''
  };
};

const goBack = () => {
  router.back();
};

const formattedFechaCreacion = computed(() => formData.value.fechaCreacion);

// Cargar idiomas disponibles y comprobar si estamos en modo edición
onMounted(() => {
  axios.get('/api/idiomas')
    .then(response => {
      idiomas.value = response.data;
    })
    .catch(error => {
      console.error('Error al cargar idiomas:', error);
      toast.error('Error al cargar idiomas.');
    });

  const id = route.query.id || route.params.id;
  if (id) {
    isEditMode.value = true;
    loadPalabraDetails(id);
  }
});

</script>

<style scoped>
.add-palabra-form {
  max-width: 500px;
  margin: auto;
  padding: 20px;
  border: 1px solid #ddd;
  border-radius: 8px;
  background-color: #f9f9f9;
  margin-top: 100px;
 
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
  box-sizing: border-box;
}

.form-group textarea {
  height: 80px;
  resize: vertical;
  min-height: 80px;
  max-height: 80px;
}

.form-group .numeric-right {
  width: 15%;

}

.small-input {
  font-size: 14px;
  padding: 6px;

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
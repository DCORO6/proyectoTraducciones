<template>
  <div class="page-background">
  <form @submit.prevent="handleSubmit" class="add-idioma-form">
    <h2 class="title-form">{{ isEditMode ? 'Actualizar idioma' : 'Agregar idioma' }}</h2>

    <div class="form-group">
      <label for="nombre">Nombre:</label>
      <input type="text" id="nombre" v-model="formData.nombre" required class="form-control" maxlength="30"/>
    </div>
    <div class="form-group">
      <label for="codigoIso">Código ISO:</label>
      <input
        type="text"
        id="codigoIso"
        v-model="formData.codigoIso"
        required
        class="form-control"
        :class="{ 'is-invalid': !isCodigoIsoValid }"
        maxlength="3"
        minlength="3"
      />
    </div>
    <div class="form-group">
      <label for="alfabeto">Alfabeto:</label>
      <select id="alfabeto" v-model="formData.alfabeto" required class="form-control">
        <option value="">Selecciona un alfabeto</option>
        <option v-for="alfabeto in formattedAlfabetos" :key="alfabeto.value" :value="alfabeto.value">
          {{ alfabeto.label }}
        </option>
      </select>
    </div>
    <div class="form-group">
      <label for="region">Región:</label>
      <input type="text" id="region" v-model="formData.region" required class="form-control" maxlength="50"/>
    </div>
    <div class="form-buttons">
      <button type="submit" class="btn-submit">
        {{ isEditMode ? 'Actualizar' : 'Agregar' }}
      </button>
      <button type="button" class="btn-back" @click="goBack">Volver</button>
    </div>
  </form>
  </div>
</template>

<script setup>
import { ref, onMounted, computed } from 'vue';
import axios from 'axios';
import { useRouter, useRoute } from 'vue-router';
import { useToast } from 'vue-toast-notification';

const router = useRouter();
const route = useRoute();
const toast = useToast();

const isEditMode = ref(false);

const formData = ref({
  nombre: '',
  codigoIso: '',
  alfabeto: '',
  region: ''
});

const alfabetos = ref([
  'LATINO',
  'TIBETANO',
  'ARMENIO',
  'ÁRABE',
  'CIRÍLICO',
  'COREANO',
  'GEORGIANO'
]);

const formatAlfabeto = (alfabeto) => {
  const lowerCase = alfabeto.toLowerCase();
  return lowerCase.charAt(0).toUpperCase() + lowerCase.slice(1);
};

const formattedAlfabetos = computed(() => {
  return alfabetos.value.map(alfabeto => ({
    value: alfabeto,
    label: formatAlfabeto(alfabeto)
  }));
});

const isCodigoIsoValid = computed(() => {
  const codigoIso = formData.value.codigoIso.trim();
  return /^[A-Za-z]{3}$/.test(codigoIso);
});

const loadIdiomaDetails = async (id) => {
  try {
    const response = await axios.get(`/api/idiomas/${id}`);
    const idioma = response.data;
    formData.value = {
      nombre: idioma.nombre,
      codigoIso: idioma.codigoIso,
      alfabeto: idioma.alfabeto,
      region: idioma.region
    };
  } catch (error) {
    console.error('Error al cargar el idioma:', error);
    toast.error('Error al cargar los detalles del idioma');
  }
};

const handleSubmit = () => {
  if (!isCodigoIsoValid.value) {
    return; 
  }
  formData.value.codigoIso = formData.value.codigoIso.toUpperCase();
  const idiomaData = { ...formData.value };
  const id = route.query.id || route.params.id;

  if (isEditMode.value) {
    axios.put(`/api/idiomas/${id}`, idiomaData)
      .then(() => {
        toast.success('Idioma '+idiomaData.nombre+' actualizado exitosamente');
        router.push('/idiomas');
      })
      .catch(error => {
        console.error('Error al actualizar idioma:', error);
        toast.error('Error al actualizar el idioma '+ idiomaData.nombre);
      });
  } else {
    axios.post('/api/idiomas', idiomaData)
      .then(() => {
        toast.success('Idioma '+idiomaData.nombre+' agregado exitosamente');
        router.push('/idiomas');
        resetForm();
      })
      .catch(error => {
        console.error('Error al crear idioma:', error);
        toast.error('Error al agregar el idioma '+ idiomaData.nombre);
      });
  }
};

const resetForm = () => {
  formData.value = {
    nombre: '',
    codigoIso: '',
    alfabeto: '',
    region: ''
  };
};

const goBack = () => {
  router.back();
};

onMounted(() => {
  const id = route.query.id || route.params.id;
  if (id) {
    isEditMode.value = true;
    loadIdiomaDetails(id);
  }
});
</script>

<style scoped>

.title-form {
  text-align: center;
  margin-bottom: 10px;
  
}

.add-idioma-form {
  max-width: 300px;
  margin: auto;
  padding: 20px;
  border: 1px solid #ddd;
  border-radius: 8px;
  background-color: #f9f9f9;
  margin-top: 200px;
  max-height: 80vh; 
  overflow-y: auto; 

}


.page-background {
  background-image: url('@/assets/img/paises.jpg');
  background-size: cover;
  background-position: center;
  background-repeat: no-repeat;
  min-height: 100vh; 
  padding: 20px; 
  


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


.form-buttons {
  display: flex;
  justify-content: space-between;
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
</style>

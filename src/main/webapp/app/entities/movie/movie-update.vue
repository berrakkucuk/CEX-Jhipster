<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" novalidate v-on:submit.prevent="save()">
        <h2
          id="cinemaSystemApp.movie.home.createOrEditLabel"
          data-cy="MovieCreateUpdateHeading"
          v-text="t$('cinemaSystemApp.movie.home.createOrEditLabel')"
        ></h2>
        <div>
          <div class="form-group" v-if="movie.id">
            <label for="id" v-text="t$('global.field.id')"></label>
            <input type="text" class="form-control" id="id" name="id" v-model="movie.id" readonly />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="t$('cinemaSystemApp.movie.title')" for="movie-title"></label>
            <input
              type="text"
              class="form-control"
              name="title"
              id="movie-title"
              data-cy="title"
              :class="{ valid: !v$.title.$invalid, invalid: v$.title.$invalid }"
              v-model="v$.title.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="t$('cinemaSystemApp.movie.genre')" for="movie-genre"></label>
            <input
              type="text"
              class="form-control"
              name="genre"
              id="movie-genre"
              data-cy="genre"
              :class="{ valid: !v$.genre.$invalid, invalid: v$.genre.$invalid }"
              v-model="v$.genre.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="t$('cinemaSystemApp.movie.duration')" for="movie-duration"></label>
            <input
              type="number"
              class="form-control"
              name="duration"
              id="movie-duration"
              data-cy="duration"
              :class="{ valid: !v$.duration.$invalid, invalid: v$.duration.$invalid }"
              v-model.number="v$.duration.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="t$('cinemaSystemApp.movie.releaseDate')" for="movie-releaseDate"></label>
            <b-input-group class="mb-3">
              <b-input-group-prepend>
                <b-form-datepicker
                  aria-controls="movie-releaseDate"
                  v-model="v$.releaseDate.$model"
                  name="releaseDate"
                  class="form-control"
                  :locale="currentLanguage"
                  button-only
                  today-button
                  reset-button
                  close-button
                >
                </b-form-datepicker>
              </b-input-group-prepend>
              <b-form-input
                id="movie-releaseDate"
                data-cy="releaseDate"
                type="text"
                class="form-control"
                name="releaseDate"
                :class="{ valid: !v$.releaseDate.$invalid, invalid: v$.releaseDate.$invalid }"
                v-model="v$.releaseDate.$model"
              />
            </b-input-group>
          </div>
        </div>
        <div>
          <button type="button" id="cancel-save" data-cy="entityCreateCancelButton" class="btn btn-secondary" v-on:click="previousState()">
            <font-awesome-icon icon="ban"></font-awesome-icon>&nbsp;<span v-text="t$('entity.action.cancel')"></span>
          </button>
          <button
            type="submit"
            id="save-entity"
            data-cy="entityCreateSaveButton"
            :disabled="v$.$invalid || isSaving"
            class="btn btn-primary"
          >
            <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="t$('entity.action.save')"></span>
          </button>
        </div>
      </form>
    </div>
  </div>
</template>
<script lang="ts" src="./movie-update.component.ts"></script>

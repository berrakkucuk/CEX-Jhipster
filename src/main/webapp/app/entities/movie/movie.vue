<template>
  <div>
    <h2 id="page-heading" data-cy="MovieHeading">
      <span v-text="t$('cinemaSystemApp.movie.home.title')" id="movie-heading"></span>
      <div class="d-flex justify-content-end">
        <button class="btn btn-info mr-2" v-on:click="handleSyncList" :disabled="isFetching">
          <font-awesome-icon icon="sync" :spin="isFetching"></font-awesome-icon>
          <span v-text="t$('cinemaSystemApp.movie.home.refreshListLabel')"></span>
        </button>
        <router-link :to="{ name: 'MovieCreate' }" custom v-slot="{ navigate }">
          <button
            @click="navigate"
            id="jh-create-entity"
            data-cy="entityCreateButton"
            class="btn btn-primary jh-create-entity create-movie"
          >
            <font-awesome-icon icon="plus"></font-awesome-icon>
            <span v-text="t$('cinemaSystemApp.movie.home.createLabel')"></span>
          </button>
        </router-link>
      </div>
    </h2>
    <br />
    <div class="alert alert-warning" v-if="!isFetching && movies && movies.length === 0">
      <span v-text="t$('cinemaSystemApp.movie.home.notFound')"></span>
    </div>
    <div class="table-responsive" v-if="movies && movies.length > 0">
      <table class="table table-striped" aria-describedby="movies">
        <thead>
          <tr>
            <th scope="row" v-on:click="changeOrder('id')">
              <span v-text="t$('global.field.id')"></span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'id'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('title')">
              <span v-text="t$('cinemaSystemApp.movie.title')"></span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'title'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('genre')">
              <span v-text="t$('cinemaSystemApp.movie.genre')"></span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'genre'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('duration')">
              <span v-text="t$('cinemaSystemApp.movie.duration')"></span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'duration'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('releaseDate')">
              <span v-text="t$('cinemaSystemApp.movie.releaseDate')"></span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'releaseDate'"></jhi-sort-indicator>
            </th>
            <th scope="row"></th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="movie in movies" :key="movie.id" data-cy="entityTable">
            <td>
              <router-link :to="{ name: 'MovieView', params: { movieId: movie.id } }">{{ movie.id }}</router-link>
            </td>
            <td>{{ movie.title }}</td>
            <td>{{ movie.genre }}</td>
            <td>{{ movie.duration }}</td>
            <td>{{ movie.releaseDate }}</td>
            <td class="text-right">
              <div class="btn-group">
                <router-link :to="{ name: 'MovieView', params: { movieId: movie.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-info btn-sm details" data-cy="entityDetailsButton">
                    <font-awesome-icon icon="eye"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="t$('entity.action.view')"></span>
                  </button>
                </router-link>
                <router-link :to="{ name: 'MovieEdit', params: { movieId: movie.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-primary btn-sm edit" data-cy="entityEditButton">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="t$('entity.action.edit')"></span>
                  </button>
                </router-link>
                <b-button
                  v-on:click="prepareRemove(movie)"
                  variant="danger"
                  class="btn btn-sm"
                  data-cy="entityDeleteButton"
                  v-b-modal.removeEntity
                >
                  <font-awesome-icon icon="times"></font-awesome-icon>
                  <span class="d-none d-md-inline" v-text="t$('entity.action.delete')"></span>
                </b-button>
              </div>
            </td>
          </tr>
        </tbody>
      </table>
    </div>
    <b-modal ref="removeEntity" id="removeEntity">
      <template #modal-title>
        <span id="cinemaSystemApp.movie.delete.question" data-cy="movieDeleteDialogHeading" v-text="t$('entity.delete.title')"></span>
      </template>
      <div class="modal-body">
        <p id="jhi-delete-movie-heading" v-text="t$('cinemaSystemApp.movie.delete.question', { id: removeId })"></p>
      </div>
      <template #modal-footer>
        <div>
          <button type="button" class="btn btn-secondary" v-text="t$('entity.action.cancel')" v-on:click="closeDialog()"></button>
          <button
            type="button"
            class="btn btn-primary"
            id="jhi-confirm-delete-movie"
            data-cy="entityConfirmDeleteButton"
            v-text="t$('entity.action.delete')"
            v-on:click="removeMovie()"
          ></button>
        </div>
      </template>
    </b-modal>
    <div v-show="movies && movies.length > 0">
      <div class="row justify-content-center">
        <jhi-item-count :page="page" :total="queryCount" :itemsPerPage="itemsPerPage"></jhi-item-count>
      </div>
      <div class="row justify-content-center">
        <b-pagination size="md" :total-rows="totalItems" v-model="page" :per-page="itemsPerPage"></b-pagination>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./movie.component.ts"></script>

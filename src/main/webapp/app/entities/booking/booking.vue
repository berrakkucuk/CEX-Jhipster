<template>
  <div>
    <h2 id="page-heading" data-cy="BookingHeading">
      <span v-text="t$('cinemaSystemApp.booking.home.title')" id="booking-heading"></span>
      <div class="d-flex justify-content-end">
        <button class="btn btn-info mr-2" v-on:click="handleSyncList" :disabled="isFetching">
          <font-awesome-icon icon="sync" :spin="isFetching"></font-awesome-icon>
          <span v-text="t$('cinemaSystemApp.booking.home.refreshListLabel')"></span>
        </button>
        <router-link :to="{ name: 'BookingCreate' }" custom v-slot="{ navigate }">
          <button
            @click="navigate"
            id="jh-create-entity"
            data-cy="entityCreateButton"
            class="btn btn-primary jh-create-entity create-booking"
          >
            <font-awesome-icon icon="plus"></font-awesome-icon>
            <span v-text="t$('cinemaSystemApp.booking.home.createLabel')"></span>
          </button>
        </router-link>
      </div>
    </h2>
    <br />
    <div class="alert alert-warning" v-if="!isFetching && bookings && bookings.length === 0">
      <span v-text="t$('cinemaSystemApp.booking.home.notFound')"></span>
    </div>
    <div class="table-responsive" v-if="bookings && bookings.length > 0">
      <table class="table table-striped" aria-describedby="bookings">
        <thead>
          <tr>
            <th scope="row" v-on:click="changeOrder('id')">
              <span v-text="t$('global.field.id')"></span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'id'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('movieTime')">
              <span v-text="t$('cinemaSystemApp.booking.movieTime')"></span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'movieTime'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('paymentStatus')">
              <span v-text="t$('cinemaSystemApp.booking.paymentStatus')"></span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'paymentStatus'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('movie.id')">
              <span v-text="t$('cinemaSystemApp.booking.movie')"></span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'movie.id'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('customer.id')">
              <span v-text="t$('cinemaSystemApp.booking.customer')"></span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'customer.id'"></jhi-sort-indicator>
            </th>
            <th scope="row"></th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="booking in bookings" :key="booking.id" data-cy="entityTable">
            <td>
              <router-link :to="{ name: 'BookingView', params: { bookingId: booking.id } }">{{ booking.id }}</router-link>
            </td>
            <td>{{ booking.movieTime }}</td>
            <td>{{ booking.paymentStatus }}</td>
            <td>
              <div v-if="booking.movie">
                <router-link :to="{ name: 'MovieView', params: { movieId: booking.movie.id } }">{{ booking.movie.id }}</router-link>
              </div>
            </td>
            <td>
              <div v-if="booking.customer">
                <router-link :to="{ name: 'CustomerView', params: { customerId: booking.customer.id } }">{{
                  booking.customer.id
                }}</router-link>
              </div>
            </td>
            <td class="text-right">
              <div class="btn-group">
                <router-link :to="{ name: 'BookingView', params: { bookingId: booking.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-info btn-sm details" data-cy="entityDetailsButton">
                    <font-awesome-icon icon="eye"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="t$('entity.action.view')"></span>
                  </button>
                </router-link>
                <router-link :to="{ name: 'BookingEdit', params: { bookingId: booking.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-primary btn-sm edit" data-cy="entityEditButton">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="t$('entity.action.edit')"></span>
                  </button>
                </router-link>
                <b-button
                  v-on:click="prepareRemove(booking)"
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
        <span id="cinemaSystemApp.booking.delete.question" data-cy="bookingDeleteDialogHeading" v-text="t$('entity.delete.title')"></span>
      </template>
      <div class="modal-body">
        <p id="jhi-delete-booking-heading" v-text="t$('cinemaSystemApp.booking.delete.question', { id: removeId })"></p>
      </div>
      <template #modal-footer>
        <div>
          <button type="button" class="btn btn-secondary" v-text="t$('entity.action.cancel')" v-on:click="closeDialog()"></button>
          <button
            type="button"
            class="btn btn-primary"
            id="jhi-confirm-delete-booking"
            data-cy="entityConfirmDeleteButton"
            v-text="t$('entity.action.delete')"
            v-on:click="removeBooking()"
          ></button>
        </div>
      </template>
    </b-modal>
    <div v-show="bookings && bookings.length > 0">
      <div class="row justify-content-center">
        <jhi-item-count :page="page" :total="queryCount" :itemsPerPage="itemsPerPage"></jhi-item-count>
      </div>
      <div class="row justify-content-center">
        <b-pagination size="md" :total-rows="totalItems" v-model="page" :per-page="itemsPerPage"></b-pagination>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./booking.component.ts"></script>

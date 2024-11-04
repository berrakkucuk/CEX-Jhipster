<template>
  <div>
    <h2 id="page-heading" data-cy="SeatHeading">
      <span v-text="t$('cinemaSystemApp.seat.home.title')" id="seat-heading"></span>
      <div class="d-flex justify-content-end">
        <button class="btn btn-info mr-2" v-on:click="handleSyncList" :disabled="isFetching">
          <font-awesome-icon icon="sync" :spin="isFetching"></font-awesome-icon>
          <span v-text="t$('cinemaSystemApp.seat.home.refreshListLabel')"></span>
        </button>
        <router-link :to="{ name: 'SeatCreate' }" custom v-slot="{ navigate }">
          <button @click="navigate" id="jh-create-entity" data-cy="entityCreateButton" class="btn btn-primary jh-create-entity create-seat">
            <font-awesome-icon icon="plus"></font-awesome-icon>
            <span v-text="t$('cinemaSystemApp.seat.home.createLabel')"></span>
          </button>
        </router-link>
      </div>
    </h2>
    <br />
    <div class="alert alert-warning" v-if="!isFetching && seats && seats.length === 0">
      <span v-text="t$('cinemaSystemApp.seat.home.notFound')"></span>
    </div>
    <div class="table-responsive" v-if="seats && seats.length > 0">
      <table class="table table-striped" aria-describedby="seats">
        <thead>
          <tr>
            <th scope="row"><span v-text="t$('global.field.id')"></span></th>
            <th scope="row"><span v-text="t$('cinemaSystemApp.seat.seatNumber')"></span></th>
            <th scope="row"><span v-text="t$('cinemaSystemApp.seat.seatType')"></span></th>
            <th scope="row"><span v-text="t$('cinemaSystemApp.seat.booking')"></span></th>
            <th scope="row"><span v-text="t$('cinemaSystemApp.seat.screen')"></span></th>
            <th scope="row"></th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="seat in seats" :key="seat.id" data-cy="entityTable">
            <td>
              <router-link :to="{ name: 'SeatView', params: { seatId: seat.id } }">{{ seat.id }}</router-link>
            </td>
            <td>{{ seat.seatNumber }}</td>
            <td>{{ seat.seatType }}</td>
            <td>
              <div v-if="seat.booking">
                <router-link :to="{ name: 'BookingView', params: { bookingId: seat.booking.id } }">{{ seat.booking.id }}</router-link>
              </div>
            </td>
            <td>
              <div v-if="seat.screen">
                <router-link :to="{ name: 'ScreenView', params: { screenId: seat.screen.id } }">{{ seat.screen.id }}</router-link>
              </div>
            </td>
            <td class="text-right">
              <div class="btn-group">
                <router-link :to="{ name: 'SeatView', params: { seatId: seat.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-info btn-sm details" data-cy="entityDetailsButton">
                    <font-awesome-icon icon="eye"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="t$('entity.action.view')"></span>
                  </button>
                </router-link>
                <router-link :to="{ name: 'SeatEdit', params: { seatId: seat.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-primary btn-sm edit" data-cy="entityEditButton">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="t$('entity.action.edit')"></span>
                  </button>
                </router-link>
                <b-button
                  v-on:click="prepareRemove(seat)"
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
        <span id="cinemaSystemApp.seat.delete.question" data-cy="seatDeleteDialogHeading" v-text="t$('entity.delete.title')"></span>
      </template>
      <div class="modal-body">
        <p id="jhi-delete-seat-heading" v-text="t$('cinemaSystemApp.seat.delete.question', { id: removeId })"></p>
      </div>
      <template #modal-footer>
        <div>
          <button type="button" class="btn btn-secondary" v-text="t$('entity.action.cancel')" v-on:click="closeDialog()"></button>
          <button
            type="button"
            class="btn btn-primary"
            id="jhi-confirm-delete-seat"
            data-cy="entityConfirmDeleteButton"
            v-text="t$('entity.action.delete')"
            v-on:click="removeSeat()"
          ></button>
        </div>
      </template>
    </b-modal>
  </div>
</template>

<script lang="ts" src="./seat.component.ts"></script>

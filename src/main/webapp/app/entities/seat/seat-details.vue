<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="seat">
        <h2 class="jh-entity-heading" data-cy="seatDetailsHeading">
          <span v-text="t$('cinemaSystemApp.seat.detail.title')"></span> {{ seat.id }}
        </h2>
        <dl class="row jh-entity-details">
          <dt>
            <span v-text="t$('cinemaSystemApp.seat.seatNumber')"></span>
          </dt>
          <dd>
            <span>{{ seat.seatNumber }}</span>
          </dd>
          <dt>
            <span v-text="t$('cinemaSystemApp.seat.seatType')"></span>
          </dt>
          <dd>
            <span>{{ seat.seatType }}</span>
          </dd>
          <dt>
            <span v-text="t$('cinemaSystemApp.seat.booking')"></span>
          </dt>
          <dd>
            <div v-if="seat.booking">
              <router-link :to="{ name: 'BookingView', params: { bookingId: seat.booking.id } }">{{ seat.booking.id }}</router-link>
            </div>
          </dd>
          <dt>
            <span v-text="t$('cinemaSystemApp.seat.screen')"></span>
          </dt>
          <dd>
            <div v-if="seat.screen">
              <router-link :to="{ name: 'ScreenView', params: { screenId: seat.screen.id } }">{{ seat.screen.id }}</router-link>
            </div>
          </dd>
        </dl>
        <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="t$('entity.action.back')"></span>
        </button>
        <router-link v-if="seat.id" :to="{ name: 'SeatEdit', params: { seatId: seat.id } }" custom v-slot="{ navigate }">
          <button @click="navigate" class="btn btn-primary">
            <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="t$('entity.action.edit')"></span>
          </button>
        </router-link>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./seat-details.component.ts"></script>

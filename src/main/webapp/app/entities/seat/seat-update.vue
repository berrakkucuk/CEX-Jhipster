<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" novalidate v-on:submit.prevent="save()">
        <h2
          id="cinemaSystemApp.seat.home.createOrEditLabel"
          data-cy="SeatCreateUpdateHeading"
          v-text="t$('cinemaSystemApp.seat.home.createOrEditLabel')"
        ></h2>
        <div>
          <div class="form-group" v-if="seat.id">
            <label for="id" v-text="t$('global.field.id')"></label>
            <input type="text" class="form-control" id="id" name="id" v-model="seat.id" readonly />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="t$('cinemaSystemApp.seat.seatNumber')" for="seat-seatNumber"></label>
            <input
              type="text"
              class="form-control"
              name="seatNumber"
              id="seat-seatNumber"
              data-cy="seatNumber"
              :class="{ valid: !v$.seatNumber.$invalid, invalid: v$.seatNumber.$invalid }"
              v-model="v$.seatNumber.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="t$('cinemaSystemApp.seat.seatType')" for="seat-seatType"></label>
            <input
              type="text"
              class="form-control"
              name="seatType"
              id="seat-seatType"
              data-cy="seatType"
              :class="{ valid: !v$.seatType.$invalid, invalid: v$.seatType.$invalid }"
              v-model="v$.seatType.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="t$('cinemaSystemApp.seat.booking')" for="seat-booking"></label>
            <select class="form-control" id="seat-booking" data-cy="booking" name="booking" v-model="seat.booking">
              <option v-bind:value="null"></option>
              <option
                v-bind:value="seat.booking && bookingOption.id === seat.booking.id ? seat.booking : bookingOption"
                v-for="bookingOption in bookings"
                :key="bookingOption.id"
              >
                {{ bookingOption.id }}
              </option>
            </select>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="t$('cinemaSystemApp.seat.screen')" for="seat-screen"></label>
            <select class="form-control" id="seat-screen" data-cy="screen" name="screen" v-model="seat.screen">
              <option v-bind:value="null"></option>
              <option
                v-bind:value="seat.screen && screenOption.id === seat.screen.id ? seat.screen : screenOption"
                v-for="screenOption in screens"
                :key="screenOption.id"
              >
                {{ screenOption.id }}
              </option>
            </select>
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
<script lang="ts" src="./seat-update.component.ts"></script>

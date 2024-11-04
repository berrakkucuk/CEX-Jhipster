<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" novalidate v-on:submit.prevent="save()">
        <h2
          id="cinemaSystemApp.booking.home.createOrEditLabel"
          data-cy="BookingCreateUpdateHeading"
          v-text="t$('cinemaSystemApp.booking.home.createOrEditLabel')"
        ></h2>
        <div>
          <div class="form-group" v-if="booking.id">
            <label for="id" v-text="t$('global.field.id')"></label>
            <input type="text" class="form-control" id="id" name="id" v-model="booking.id" readonly />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="t$('cinemaSystemApp.booking.movieTime')" for="booking-movieTime"></label>
            <b-input-group class="mb-3">
              <b-input-group-prepend>
                <b-form-datepicker
                  aria-controls="booking-movieTime"
                  v-model="v$.movieTime.$model"
                  name="movieTime"
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
                id="booking-movieTime"
                data-cy="movieTime"
                type="text"
                class="form-control"
                name="movieTime"
                :class="{ valid: !v$.movieTime.$invalid, invalid: v$.movieTime.$invalid }"
                v-model="v$.movieTime.$model"
              />
            </b-input-group>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="t$('cinemaSystemApp.booking.paymentStatus')" for="booking-paymentStatus"></label>
            <input
              type="text"
              class="form-control"
              name="paymentStatus"
              id="booking-paymentStatus"
              data-cy="paymentStatus"
              :class="{ valid: !v$.paymentStatus.$invalid, invalid: v$.paymentStatus.$invalid }"
              v-model="v$.paymentStatus.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="t$('cinemaSystemApp.booking.movie')" for="booking-movie"></label>
            <select class="form-control" id="booking-movie" data-cy="movie" name="movie" v-model="booking.movie">
              <option v-bind:value="null"></option>
              <option
                v-bind:value="booking.movie && movieOption.id === booking.movie.id ? booking.movie : movieOption"
                v-for="movieOption in movies"
                :key="movieOption.id"
              >
                {{ movieOption.id }}
              </option>
            </select>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="t$('cinemaSystemApp.booking.customer')" for="booking-customer"></label>
            <select class="form-control" id="booking-customer" data-cy="customer" name="customer" v-model="booking.customer">
              <option v-bind:value="null"></option>
              <option
                v-bind:value="booking.customer && customerOption.id === booking.customer.id ? booking.customer : customerOption"
                v-for="customerOption in customers"
                :key="customerOption.id"
              >
                {{ customerOption.id }}
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
<script lang="ts" src="./booking-update.component.ts"></script>

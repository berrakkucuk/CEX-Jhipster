import { defineComponent, inject, onMounted, ref, type Ref } from 'vue';
import { useI18n } from 'vue-i18n';

import SeatService from './seat.service';
import { type ISeat } from '@/shared/model/seat.model';
import { useAlertService } from '@/shared/alert/alert.service';

export default defineComponent({
  compatConfig: { MODE: 3 },
  name: 'Seat',
  setup() {
    const { t: t$ } = useI18n();
    const seatService = inject('seatService', () => new SeatService());
    const alertService = inject('alertService', () => useAlertService(), true);

    const seats: Ref<ISeat[]> = ref([]);

    const isFetching = ref(false);

    const clear = () => {};

    const retrieveSeats = async () => {
      isFetching.value = true;
      try {
        const res = await seatService().retrieve();
        seats.value = res.data;
      } catch (err) {
        alertService.showHttpError(err.response);
      } finally {
        isFetching.value = false;
      }
    };

    const handleSyncList = () => {
      retrieveSeats();
    };

    onMounted(async () => {
      await retrieveSeats();
    });

    const removeId: Ref<number> = ref(null);
    const removeEntity = ref<any>(null);
    const prepareRemove = (instance: ISeat) => {
      removeId.value = instance.id;
      removeEntity.value.show();
    };
    const closeDialog = () => {
      removeEntity.value.hide();
    };
    const removeSeat = async () => {
      try {
        await seatService().delete(removeId.value);
        const message = t$('cinemaSystemApp.seat.deleted', { param: removeId.value }).toString();
        alertService.showInfo(message, { variant: 'danger' });
        removeId.value = null;
        retrieveSeats();
        closeDialog();
      } catch (error) {
        alertService.showHttpError(error.response);
      }
    };

    return {
      seats,
      handleSyncList,
      isFetching,
      retrieveSeats,
      clear,
      removeId,
      removeEntity,
      prepareRemove,
      closeDialog,
      removeSeat,
      t$,
    };
  },
});

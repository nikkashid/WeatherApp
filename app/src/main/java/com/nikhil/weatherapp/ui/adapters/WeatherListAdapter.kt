package com.nikhil.weatherapp.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.nikhil.weatherapp.R
import com.nikhil.weatherapp.databinding.CityWeatherItemBinding
import com.nikhil.weatherapp.entities.WeatherEntity

class WeatherListAdapter(iClickListener: IClickListener) :
    ListAdapter<WeatherEntity, WeatherListAdapter.WeatherViewHolder>(differCallback) {

    var iClickListener: IClickListener = iClickListener

    inner class WeatherViewHolder(itemView: CityWeatherItemBinding) :
        RecyclerView.ViewHolder(itemView.root) {

        var binding: CityWeatherItemBinding = itemView
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WeatherViewHolder {
        val weatherItemBinding: CityWeatherItemBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.city_weather_item, parent, false
        )

        return WeatherViewHolder(weatherItemBinding)
    }

    override fun onBindViewHolder(holder: WeatherViewHolder, position: Int) {
        val weatherEntity = getItem(position)
        holder.binding.cityAdapterItem = weatherEntity

        holder.binding.llMainItem.setOnClickListener(View.OnClickListener {
            iClickListener.onClickListner(weatherEntity)
        })
    }

    companion object {
        private val differCallback = object : DiffUtil.ItemCallback<WeatherEntity>() {

            override fun areItemsTheSame(oldItem: WeatherEntity, newItem: WeatherEntity): Boolean {
                return oldItem.cityName == newItem.cityName
            }

            override fun areContentsTheSame(
                oldItem: WeatherEntity,
                newItem: WeatherEntity
            ): Boolean {
                return oldItem == newItem
            }
        }
    }

    interface IClickListener {
        fun onClickListner(weatherEntity: WeatherEntity)
    }

}
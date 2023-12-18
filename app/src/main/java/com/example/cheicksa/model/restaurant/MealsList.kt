package com.example.cheicksa.model.restaurant

import com.google.apphosting.datastore.testing.DatastoreTestTrace.FirestoreV1Action.ListCollectionIds
import kotlinx.serialization.Serializable

@Serializable
data class MealsList(
    val mealListCollectionIds: String = "",
    val mealTitle: String,
    var cards: List<OrderScreenCardData>,
){
    constructor() : this("","", emptyList())
}
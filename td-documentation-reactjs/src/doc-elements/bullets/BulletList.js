import React from 'react'
import './BulletList.css'

import LeftRightTimeLine from './kinds/LeftRightTimeLine'
import Venn from './kinds/Venn'
import RevealBoxes from './kinds/RevealBoxes'

const DefaultBulletList = ({tight, ...props}) => {
    const className = "content-block" + (tight ? " tight" : "")
    return (<ul className={className}><props.elementsLibrary.DocElement {...props}/></ul>)
}

const types = {LeftRightTimeLine, Venn}
const presentationTypes = {RevealBoxes, LeftRightTimeLine, Venn}

const BulletList = (props) => {
    const type = listType(props, 'bulletListType')

    if (type === null) {
        return <DefaultBulletList {...props}/>
    }

    const Bullets = valueByIdWithWarning(types, type)
    return <Bullets {...props}/>
}

const NoBullets = () => <div className="content-block">No bullets type found</div>

const PresentationBulletList = (props) => {
    const type = presentationListType(props)

    if (type === null) {
        return <DefaultBulletList {...props}/>
    }

    const PresentationBullets = valueByIdWithWarning(presentationTypes, type)
    const isPresentation = typeof props.slideIdx !== 'undefined'

    return <PresentationBullets isPresentation={isPresentation} {...props}/>
}

const presentationNumberOfSlides = ({content, ...props}) => {
    const type = presentationListType(props)
    return (type === null) ? 1 : content.length
}

function valueByIdWithWarning(dict, type) {
    if (! dict.hasOwnProperty(type)) {
        console.warn("can't find bullets list type: " + type)
        return NoBullets
    }

    return dict[type]
}

function presentationListType(props) {
    return listType(props, 'bulletListType') ||
        listType(props, 'presentationBulletListType')
}

function listType(props, key) {
    return (typeof props.renderingMeta === 'undefined') ? null : props.renderingMeta.paramValue(key)
}

const presentationBulletListHandler = {component: PresentationBulletList,
    numberOfSlides: presentationNumberOfSlides}

export {BulletList, presentationBulletListHandler}
